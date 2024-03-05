import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICandidate } from 'app/entities/candidate/candidate.model';
import { CandidateService } from 'app/entities/candidate/service/candidate.service';
import { CertificationService } from '../service/certification.service';
import { ICertification } from '../certification.model';
import { CertificationFormService } from './certification-form.service';

import { CertificationUpdateComponent } from './certification-update.component';

describe('Certification Management Update Component', () => {
  let comp: CertificationUpdateComponent;
  let fixture: ComponentFixture<CertificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let certificationFormService: CertificationFormService;
  let certificationService: CertificationService;
  let candidateService: CandidateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CertificationUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CertificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CertificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    certificationFormService = TestBed.inject(CertificationFormService);
    certificationService = TestBed.inject(CertificationService);
    candidateService = TestBed.inject(CandidateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Candidate query and add missing value', () => {
      const certification: ICertification = { id: 456 };
      const certificationCandidate: ICandidate = { id: 13598 };
      certification.certificationCandidate = certificationCandidate;
      const candidate: ICandidate = { id: 9037 };
      certification.candidate = candidate;

      const candidateCollection: ICandidate[] = [{ id: 8879 }];
      jest.spyOn(candidateService, 'query').mockReturnValue(of(new HttpResponse({ body: candidateCollection })));
      const additionalCandidates = [certificationCandidate, candidate];
      const expectedCollection: ICandidate[] = [...additionalCandidates, ...candidateCollection];
      jest.spyOn(candidateService, 'addCandidateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ certification });
      comp.ngOnInit();

      expect(candidateService.query).toHaveBeenCalled();
      expect(candidateService.addCandidateToCollectionIfMissing).toHaveBeenCalledWith(
        candidateCollection,
        ...additionalCandidates.map(expect.objectContaining),
      );
      expect(comp.candidatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const certification: ICertification = { id: 456 };
      const certificationCandidate: ICandidate = { id: 27069 };
      certification.certificationCandidate = certificationCandidate;
      const candidate: ICandidate = { id: 10380 };
      certification.candidate = candidate;

      activatedRoute.data = of({ certification });
      comp.ngOnInit();

      expect(comp.candidatesSharedCollection).toContain(certificationCandidate);
      expect(comp.candidatesSharedCollection).toContain(candidate);
      expect(comp.certification).toEqual(certification);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICertification>>();
      const certification = { id: 123 };
      jest.spyOn(certificationFormService, 'getCertification').mockReturnValue(certification);
      jest.spyOn(certificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ certification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: certification }));
      saveSubject.complete();

      // THEN
      expect(certificationFormService.getCertification).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(certificationService.update).toHaveBeenCalledWith(expect.objectContaining(certification));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICertification>>();
      const certification = { id: 123 };
      jest.spyOn(certificationFormService, 'getCertification').mockReturnValue({ id: null });
      jest.spyOn(certificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ certification: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: certification }));
      saveSubject.complete();

      // THEN
      expect(certificationFormService.getCertification).toHaveBeenCalled();
      expect(certificationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICertification>>();
      const certification = { id: 123 };
      jest.spyOn(certificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ certification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(certificationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCandidate', () => {
      it('Should forward to candidateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(candidateService, 'compareCandidate');
        comp.compareCandidate(entity, entity2);
        expect(candidateService.compareCandidate).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
