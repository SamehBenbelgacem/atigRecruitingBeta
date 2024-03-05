import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICandidate } from 'app/entities/candidate/candidate.model';
import { CandidateService } from 'app/entities/candidate/service/candidate.service';
import { LanguageService } from '../service/language.service';
import { ILanguage } from '../language.model';
import { LanguageFormService } from './language-form.service';

import { LanguageUpdateComponent } from './language-update.component';

describe('Language Management Update Component', () => {
  let comp: LanguageUpdateComponent;
  let fixture: ComponentFixture<LanguageUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let languageFormService: LanguageFormService;
  let languageService: LanguageService;
  let candidateService: CandidateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), LanguageUpdateComponent],
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
      .overrideTemplate(LanguageUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LanguageUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    languageFormService = TestBed.inject(LanguageFormService);
    languageService = TestBed.inject(LanguageService);
    candidateService = TestBed.inject(CandidateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Candidate query and add missing value', () => {
      const language: ILanguage = { id: 456 };
      const languageCandidate: ICandidate = { id: 3768 };
      language.languageCandidate = languageCandidate;
      const candidate: ICandidate = { id: 18962 };
      language.candidate = candidate;

      const candidateCollection: ICandidate[] = [{ id: 25835 }];
      jest.spyOn(candidateService, 'query').mockReturnValue(of(new HttpResponse({ body: candidateCollection })));
      const additionalCandidates = [languageCandidate, candidate];
      const expectedCollection: ICandidate[] = [...additionalCandidates, ...candidateCollection];
      jest.spyOn(candidateService, 'addCandidateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ language });
      comp.ngOnInit();

      expect(candidateService.query).toHaveBeenCalled();
      expect(candidateService.addCandidateToCollectionIfMissing).toHaveBeenCalledWith(
        candidateCollection,
        ...additionalCandidates.map(expect.objectContaining),
      );
      expect(comp.candidatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const language: ILanguage = { id: 456 };
      const languageCandidate: ICandidate = { id: 2067 };
      language.languageCandidate = languageCandidate;
      const candidate: ICandidate = { id: 23322 };
      language.candidate = candidate;

      activatedRoute.data = of({ language });
      comp.ngOnInit();

      expect(comp.candidatesSharedCollection).toContain(languageCandidate);
      expect(comp.candidatesSharedCollection).toContain(candidate);
      expect(comp.language).toEqual(language);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILanguage>>();
      const language = { id: 123 };
      jest.spyOn(languageFormService, 'getLanguage').mockReturnValue(language);
      jest.spyOn(languageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ language });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: language }));
      saveSubject.complete();

      // THEN
      expect(languageFormService.getLanguage).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(languageService.update).toHaveBeenCalledWith(expect.objectContaining(language));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILanguage>>();
      const language = { id: 123 };
      jest.spyOn(languageFormService, 'getLanguage').mockReturnValue({ id: null });
      jest.spyOn(languageService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ language: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: language }));
      saveSubject.complete();

      // THEN
      expect(languageFormService.getLanguage).toHaveBeenCalled();
      expect(languageService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILanguage>>();
      const language = { id: 123 };
      jest.spyOn(languageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ language });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(languageService.update).toHaveBeenCalled();
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
