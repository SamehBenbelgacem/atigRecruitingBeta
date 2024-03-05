import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICandidateAdditionalInfos } from 'app/entities/candidate-additional-infos/candidate-additional-infos.model';
import { CandidateAdditionalInfosService } from 'app/entities/candidate-additional-infos/service/candidate-additional-infos.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { ISubCategory } from 'app/entities/sub-category/sub-category.model';
import { SubCategoryService } from 'app/entities/sub-category/service/sub-category.service';
import { IProcess } from 'app/entities/process/process.model';
import { ProcessService } from 'app/entities/process/service/process.service';
import { IProcessStep } from 'app/entities/process-step/process-step.model';
import { ProcessStepService } from 'app/entities/process-step/service/process-step.service';
import { ITag } from 'app/entities/tag/tag.model';
import { TagService } from 'app/entities/tag/service/tag.service';
import { ICandidate } from '../candidate.model';
import { CandidateService } from '../service/candidate.service';
import { CandidateFormService } from './candidate-form.service';

import { CandidateUpdateComponent } from './candidate-update.component';

describe('Candidate Management Update Component', () => {
  let comp: CandidateUpdateComponent;
  let fixture: ComponentFixture<CandidateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let candidateFormService: CandidateFormService;
  let candidateService: CandidateService;
  let candidateAdditionalInfosService: CandidateAdditionalInfosService;
  let categoryService: CategoryService;
  let subCategoryService: SubCategoryService;
  let processService: ProcessService;
  let processStepService: ProcessStepService;
  let tagService: TagService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CandidateUpdateComponent],
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
      .overrideTemplate(CandidateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CandidateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    candidateFormService = TestBed.inject(CandidateFormService);
    candidateService = TestBed.inject(CandidateService);
    candidateAdditionalInfosService = TestBed.inject(CandidateAdditionalInfosService);
    categoryService = TestBed.inject(CategoryService);
    subCategoryService = TestBed.inject(SubCategoryService);
    processService = TestBed.inject(ProcessService);
    processStepService = TestBed.inject(ProcessStepService);
    tagService = TestBed.inject(TagService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call additionalInfos query and add missing value', () => {
      const candidate: ICandidate = { id: 456 };
      const additionalInfos: ICandidateAdditionalInfos = { id: 6579 };
      candidate.additionalInfos = additionalInfos;

      const additionalInfosCollection: ICandidateAdditionalInfos[] = [{ id: 18845 }];
      jest.spyOn(candidateAdditionalInfosService, 'query').mockReturnValue(of(new HttpResponse({ body: additionalInfosCollection })));
      const expectedCollection: ICandidateAdditionalInfos[] = [additionalInfos, ...additionalInfosCollection];
      jest.spyOn(candidateAdditionalInfosService, 'addCandidateAdditionalInfosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidate });
      comp.ngOnInit();

      expect(candidateAdditionalInfosService.query).toHaveBeenCalled();
      expect(candidateAdditionalInfosService.addCandidateAdditionalInfosToCollectionIfMissing).toHaveBeenCalledWith(
        additionalInfosCollection,
        additionalInfos,
      );
      expect(comp.additionalInfosCollection).toEqual(expectedCollection);
    });

    it('Should call Category query and add missing value', () => {
      const candidate: ICandidate = { id: 456 };
      const candidateCategory: ICategory = { id: 27791 };
      candidate.candidateCategory = candidateCategory;
      const category: ICategory = { id: 12789 };
      candidate.category = category;

      const categoryCollection: ICategory[] = [{ id: 3279 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [candidateCategory, category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidate });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategories.map(expect.objectContaining),
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SubCategory query and add missing value', () => {
      const candidate: ICandidate = { id: 456 };
      const candidateSubCategory: ISubCategory = { id: 30535 };
      candidate.candidateSubCategory = candidateSubCategory;
      const subCategory: ISubCategory = { id: 16607 };
      candidate.subCategory = subCategory;

      const subCategoryCollection: ISubCategory[] = [{ id: 22405 }];
      jest.spyOn(subCategoryService, 'query').mockReturnValue(of(new HttpResponse({ body: subCategoryCollection })));
      const additionalSubCategories = [candidateSubCategory, subCategory];
      const expectedCollection: ISubCategory[] = [...additionalSubCategories, ...subCategoryCollection];
      jest.spyOn(subCategoryService, 'addSubCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidate });
      comp.ngOnInit();

      expect(subCategoryService.query).toHaveBeenCalled();
      expect(subCategoryService.addSubCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        subCategoryCollection,
        ...additionalSubCategories.map(expect.objectContaining),
      );
      expect(comp.subCategoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Process query and add missing value', () => {
      const candidate: ICandidate = { id: 456 };
      const candidateProcess: IProcess = { id: 11544 };
      candidate.candidateProcess = candidateProcess;
      const process: IProcess = { id: 23711 };
      candidate.process = process;

      const processCollection: IProcess[] = [{ id: 9284 }];
      jest.spyOn(processService, 'query').mockReturnValue(of(new HttpResponse({ body: processCollection })));
      const additionalProcesses = [candidateProcess, process];
      const expectedCollection: IProcess[] = [...additionalProcesses, ...processCollection];
      jest.spyOn(processService, 'addProcessToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidate });
      comp.ngOnInit();

      expect(processService.query).toHaveBeenCalled();
      expect(processService.addProcessToCollectionIfMissing).toHaveBeenCalledWith(
        processCollection,
        ...additionalProcesses.map(expect.objectContaining),
      );
      expect(comp.processesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProcessStep query and add missing value', () => {
      const candidate: ICandidate = { id: 456 };
      const candidateProcessStep: IProcessStep = { id: 8385 };
      candidate.candidateProcessStep = candidateProcessStep;
      const processStep: IProcessStep = { id: 2394 };
      candidate.processStep = processStep;

      const processStepCollection: IProcessStep[] = [{ id: 11183 }];
      jest.spyOn(processStepService, 'query').mockReturnValue(of(new HttpResponse({ body: processStepCollection })));
      const additionalProcessSteps = [candidateProcessStep, processStep];
      const expectedCollection: IProcessStep[] = [...additionalProcessSteps, ...processStepCollection];
      jest.spyOn(processStepService, 'addProcessStepToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidate });
      comp.ngOnInit();

      expect(processStepService.query).toHaveBeenCalled();
      expect(processStepService.addProcessStepToCollectionIfMissing).toHaveBeenCalledWith(
        processStepCollection,
        ...additionalProcessSteps.map(expect.objectContaining),
      );
      expect(comp.processStepsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tag query and add missing value', () => {
      const candidate: ICandidate = { id: 456 };
      const tags: ITag[] = [{ id: 5438 }];
      candidate.tags = tags;

      const tagCollection: ITag[] = [{ id: 29957 }];
      jest.spyOn(tagService, 'query').mockReturnValue(of(new HttpResponse({ body: tagCollection })));
      const additionalTags = [...tags];
      const expectedCollection: ITag[] = [...additionalTags, ...tagCollection];
      jest.spyOn(tagService, 'addTagToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ candidate });
      comp.ngOnInit();

      expect(tagService.query).toHaveBeenCalled();
      expect(tagService.addTagToCollectionIfMissing).toHaveBeenCalledWith(tagCollection, ...additionalTags.map(expect.objectContaining));
      expect(comp.tagsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const candidate: ICandidate = { id: 456 };
      const additionalInfos: ICandidateAdditionalInfos = { id: 9039 };
      candidate.additionalInfos = additionalInfos;
      const candidateCategory: ICategory = { id: 17632 };
      candidate.candidateCategory = candidateCategory;
      const category: ICategory = { id: 15 };
      candidate.category = category;
      const candidateSubCategory: ISubCategory = { id: 1358 };
      candidate.candidateSubCategory = candidateSubCategory;
      const subCategory: ISubCategory = { id: 23700 };
      candidate.subCategory = subCategory;
      const candidateProcess: IProcess = { id: 28946 };
      candidate.candidateProcess = candidateProcess;
      const process: IProcess = { id: 26065 };
      candidate.process = process;
      const candidateProcessStep: IProcessStep = { id: 12995 };
      candidate.candidateProcessStep = candidateProcessStep;
      const processStep: IProcessStep = { id: 6659 };
      candidate.processStep = processStep;
      const tags: ITag = { id: 1813 };
      candidate.tags = [tags];

      activatedRoute.data = of({ candidate });
      comp.ngOnInit();

      expect(comp.additionalInfosCollection).toContain(additionalInfos);
      expect(comp.categoriesSharedCollection).toContain(candidateCategory);
      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.subCategoriesSharedCollection).toContain(candidateSubCategory);
      expect(comp.subCategoriesSharedCollection).toContain(subCategory);
      expect(comp.processesSharedCollection).toContain(candidateProcess);
      expect(comp.processesSharedCollection).toContain(process);
      expect(comp.processStepsSharedCollection).toContain(candidateProcessStep);
      expect(comp.processStepsSharedCollection).toContain(processStep);
      expect(comp.tagsSharedCollection).toContain(tags);
      expect(comp.candidate).toEqual(candidate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidate>>();
      const candidate = { id: 123 };
      jest.spyOn(candidateFormService, 'getCandidate').mockReturnValue(candidate);
      jest.spyOn(candidateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: candidate }));
      saveSubject.complete();

      // THEN
      expect(candidateFormService.getCandidate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(candidateService.update).toHaveBeenCalledWith(expect.objectContaining(candidate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidate>>();
      const candidate = { id: 123 };
      jest.spyOn(candidateFormService, 'getCandidate').mockReturnValue({ id: null });
      jest.spyOn(candidateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: candidate }));
      saveSubject.complete();

      // THEN
      expect(candidateFormService.getCandidate).toHaveBeenCalled();
      expect(candidateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICandidate>>();
      const candidate = { id: 123 };
      jest.spyOn(candidateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ candidate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(candidateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCandidateAdditionalInfos', () => {
      it('Should forward to candidateAdditionalInfosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(candidateAdditionalInfosService, 'compareCandidateAdditionalInfos');
        comp.compareCandidateAdditionalInfos(entity, entity2);
        expect(candidateAdditionalInfosService.compareCandidateAdditionalInfos).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCategory', () => {
      it('Should forward to categoryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoryService, 'compareCategory');
        comp.compareCategory(entity, entity2);
        expect(categoryService.compareCategory).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSubCategory', () => {
      it('Should forward to subCategoryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(subCategoryService, 'compareSubCategory');
        comp.compareSubCategory(entity, entity2);
        expect(subCategoryService.compareSubCategory).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProcess', () => {
      it('Should forward to processService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(processService, 'compareProcess');
        comp.compareProcess(entity, entity2);
        expect(processService.compareProcess).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProcessStep', () => {
      it('Should forward to processStepService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(processStepService, 'compareProcessStep');
        comp.compareProcessStep(entity, entity2);
        expect(processStepService.compareProcessStep).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTag', () => {
      it('Should forward to tagService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tagService, 'compareTag');
        comp.compareTag(entity, entity2);
        expect(tagService.compareTag).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
