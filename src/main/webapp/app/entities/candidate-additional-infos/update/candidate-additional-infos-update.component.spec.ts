import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CandidateAdditionalInfosService } from '../service/candidate-additional-infos.service';
import { ICandidateAdditionalInfos } from '../candidate-additional-infos.model';
import { CandidateAdditionalInfosFormService } from './candidate-additional-infos-form.service';

import { CandidateAdditionalInfosUpdateComponent } from './candidate-additional-infos-update.component';

describe('CandidateAdditionalInfos Management Update Component', () => {
  let comp: CandidateAdditionalInfosUpdateComponent;
  let fixture: ComponentFixture<CandidateAdditionalInfosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let candidateAdditionalInfosFormService: CandidateAdditionalInfosFormService;
  let candidateAdditionalInfosService: CandidateAdditionalInfosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CandidateAdditionalInfosUpdateComponent],
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
      .overrideTemplate(CandidateAdditionalInfosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CandidateAdditionalInfosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    candidateAdditionalInfosFormService = TestBed.inject(CandidateAdditionalInfosFormService);
    candidateAdditionalInfosService = TestBed.inject(CandidateAdditionalInfosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const candidateAdditionalInfos: ICandidateAdditionalInfos = { id: 456 };

      activatedRoute.data = of({ candidateAdditionalInfos });
      comp.ngOnInit();

      expect(comp.candidateAdditionalInfos).toEqual(candidateAdditionalInfos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidateAdditionalInfos>>();
      const candidateAdditionalInfos = { id: 123 };
      jest.spyOn(candidateAdditionalInfosFormService, 'getCandidateAdditionalInfos').mockReturnValue(candidateAdditionalInfos);
      jest.spyOn(candidateAdditionalInfosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidateAdditionalInfos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: candidateAdditionalInfos }));
      saveSubject.complete();

      // THEN
      expect(candidateAdditionalInfosFormService.getCandidateAdditionalInfos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(candidateAdditionalInfosService.update).toHaveBeenCalledWith(expect.objectContaining(candidateAdditionalInfos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidateAdditionalInfos>>();
      const candidateAdditionalInfos = { id: 123 };
      jest.spyOn(candidateAdditionalInfosFormService, 'getCandidateAdditionalInfos').mockReturnValue({ id: null });
      jest.spyOn(candidateAdditionalInfosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidateAdditionalInfos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: candidateAdditionalInfos }));
      saveSubject.complete();

      // THEN
      expect(candidateAdditionalInfosFormService.getCandidateAdditionalInfos).toHaveBeenCalled();
      expect(candidateAdditionalInfosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidateAdditionalInfos>>();
      const candidateAdditionalInfos = { id: 123 };
      jest.spyOn(candidateAdditionalInfosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidateAdditionalInfos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(candidateAdditionalInfosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
