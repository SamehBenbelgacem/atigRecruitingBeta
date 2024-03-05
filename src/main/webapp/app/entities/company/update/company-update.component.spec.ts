import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

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
import { ICompany } from '../company.model';
import { CompanyService } from '../service/company.service';
import { CompanyFormService } from './company-form.service';

import { CompanyUpdateComponent } from './company-update.component';

describe('Company Management Update Component', () => {
  let comp: CompanyUpdateComponent;
  let fixture: ComponentFixture<CompanyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companyFormService: CompanyFormService;
  let companyService: CompanyService;
  let categoryService: CategoryService;
  let subCategoryService: SubCategoryService;
  let processService: ProcessService;
  let processStepService: ProcessStepService;
  let tagService: TagService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CompanyUpdateComponent],
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
      .overrideTemplate(CompanyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companyFormService = TestBed.inject(CompanyFormService);
    companyService = TestBed.inject(CompanyService);
    categoryService = TestBed.inject(CategoryService);
    subCategoryService = TestBed.inject(SubCategoryService);
    processService = TestBed.inject(ProcessService);
    processStepService = TestBed.inject(ProcessStepService);
    tagService = TestBed.inject(TagService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Category query and add missing value', () => {
      const company: ICompany = { id: 456 };
      const companyCategory: ICategory = { id: 21153 };
      company.companyCategory = companyCategory;
      const category: ICategory = { id: 7841 };
      company.category = category;

      const categoryCollection: ICategory[] = [{ id: 29985 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [companyCategory, category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategories.map(expect.objectContaining),
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SubCategory query and add missing value', () => {
      const company: ICompany = { id: 456 };
      const companySubCategory: ISubCategory = { id: 30597 };
      company.companySubCategory = companySubCategory;
      const subCategory: ISubCategory = { id: 3125 };
      company.subCategory = subCategory;

      const subCategoryCollection: ISubCategory[] = [{ id: 1789 }];
      jest.spyOn(subCategoryService, 'query').mockReturnValue(of(new HttpResponse({ body: subCategoryCollection })));
      const additionalSubCategories = [companySubCategory, subCategory];
      const expectedCollection: ISubCategory[] = [...additionalSubCategories, ...subCategoryCollection];
      jest.spyOn(subCategoryService, 'addSubCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(subCategoryService.query).toHaveBeenCalled();
      expect(subCategoryService.addSubCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        subCategoryCollection,
        ...additionalSubCategories.map(expect.objectContaining),
      );
      expect(comp.subCategoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Process query and add missing value', () => {
      const company: ICompany = { id: 456 };
      const companyProcess: IProcess = { id: 3595 };
      company.companyProcess = companyProcess;
      const process: IProcess = { id: 20039 };
      company.process = process;

      const processCollection: IProcess[] = [{ id: 9248 }];
      jest.spyOn(processService, 'query').mockReturnValue(of(new HttpResponse({ body: processCollection })));
      const additionalProcesses = [companyProcess, process];
      const expectedCollection: IProcess[] = [...additionalProcesses, ...processCollection];
      jest.spyOn(processService, 'addProcessToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(processService.query).toHaveBeenCalled();
      expect(processService.addProcessToCollectionIfMissing).toHaveBeenCalledWith(
        processCollection,
        ...additionalProcesses.map(expect.objectContaining),
      );
      expect(comp.processesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProcessStep query and add missing value', () => {
      const company: ICompany = { id: 456 };
      const companyProcessStep: IProcessStep = { id: 6349 };
      company.companyProcessStep = companyProcessStep;
      const processStep: IProcessStep = { id: 22956 };
      company.processStep = processStep;

      const processStepCollection: IProcessStep[] = [{ id: 26470 }];
      jest.spyOn(processStepService, 'query').mockReturnValue(of(new HttpResponse({ body: processStepCollection })));
      const additionalProcessSteps = [companyProcessStep, processStep];
      const expectedCollection: IProcessStep[] = [...additionalProcessSteps, ...processStepCollection];
      jest.spyOn(processStepService, 'addProcessStepToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(processStepService.query).toHaveBeenCalled();
      expect(processStepService.addProcessStepToCollectionIfMissing).toHaveBeenCalledWith(
        processStepCollection,
        ...additionalProcessSteps.map(expect.objectContaining),
      );
      expect(comp.processStepsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tag query and add missing value', () => {
      const company: ICompany = { id: 456 };
      const tags: ITag[] = [{ id: 22578 }];
      company.tags = tags;

      const tagCollection: ITag[] = [{ id: 31102 }];
      jest.spyOn(tagService, 'query').mockReturnValue(of(new HttpResponse({ body: tagCollection })));
      const additionalTags = [...tags];
      const expectedCollection: ITag[] = [...additionalTags, ...tagCollection];
      jest.spyOn(tagService, 'addTagToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(tagService.query).toHaveBeenCalled();
      expect(tagService.addTagToCollectionIfMissing).toHaveBeenCalledWith(tagCollection, ...additionalTags.map(expect.objectContaining));
      expect(comp.tagsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const company: ICompany = { id: 456 };
      const companyCategory: ICategory = { id: 19303 };
      company.companyCategory = companyCategory;
      const category: ICategory = { id: 28604 };
      company.category = category;
      const companySubCategory: ISubCategory = { id: 21476 };
      company.companySubCategory = companySubCategory;
      const subCategory: ISubCategory = { id: 29875 };
      company.subCategory = subCategory;
      const companyProcess: IProcess = { id: 4917 };
      company.companyProcess = companyProcess;
      const process: IProcess = { id: 31816 };
      company.process = process;
      const companyProcessStep: IProcessStep = { id: 18607 };
      company.companyProcessStep = companyProcessStep;
      const processStep: IProcessStep = { id: 12162 };
      company.processStep = processStep;
      const tags: ITag = { id: 22842 };
      company.tags = [tags];

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(comp.categoriesSharedCollection).toContain(companyCategory);
      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.subCategoriesSharedCollection).toContain(companySubCategory);
      expect(comp.subCategoriesSharedCollection).toContain(subCategory);
      expect(comp.processesSharedCollection).toContain(companyProcess);
      expect(comp.processesSharedCollection).toContain(process);
      expect(comp.processStepsSharedCollection).toContain(companyProcessStep);
      expect(comp.processStepsSharedCollection).toContain(processStep);
      expect(comp.tagsSharedCollection).toContain(tags);
      expect(comp.company).toEqual(company);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompany>>();
      const company = { id: 123 };
      jest.spyOn(companyFormService, 'getCompany').mockReturnValue(company);
      jest.spyOn(companyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ company });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: company }));
      saveSubject.complete();

      // THEN
      expect(companyFormService.getCompany).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(companyService.update).toHaveBeenCalledWith(expect.objectContaining(company));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompany>>();
      const company = { id: 123 };
      jest.spyOn(companyFormService, 'getCompany').mockReturnValue({ id: null });
      jest.spyOn(companyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ company: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: company }));
      saveSubject.complete();

      // THEN
      expect(companyFormService.getCompany).toHaveBeenCalled();
      expect(companyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompany>>();
      const company = { id: 123 };
      jest.spyOn(companyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ company });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
