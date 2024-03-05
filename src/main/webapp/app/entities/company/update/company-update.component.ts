import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
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
import { CompanyService } from '../service/company.service';
import { ICompany } from '../company.model';
import { CompanyFormService, CompanyFormGroup } from './company-form.service';

@Component({
  standalone: true,
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CompanyUpdateComponent implements OnInit {
  isSaving = false;
  company: ICompany | null = null;

  categoriesSharedCollection: ICategory[] = [];
  subCategoriesSharedCollection: ISubCategory[] = [];
  processesSharedCollection: IProcess[] = [];
  processStepsSharedCollection: IProcessStep[] = [];
  tagsSharedCollection: ITag[] = [];

  editForm: CompanyFormGroup = this.companyFormService.createCompanyFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected companyService: CompanyService,
    protected companyFormService: CompanyFormService,
    protected categoryService: CategoryService,
    protected subCategoryService: SubCategoryService,
    protected processService: ProcessService,
    protected processStepService: ProcessStepService,
    protected tagService: TagService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCategory = (o1: ICategory | null, o2: ICategory | null): boolean => this.categoryService.compareCategory(o1, o2);

  compareSubCategory = (o1: ISubCategory | null, o2: ISubCategory | null): boolean => this.subCategoryService.compareSubCategory(o1, o2);

  compareProcess = (o1: IProcess | null, o2: IProcess | null): boolean => this.processService.compareProcess(o1, o2);

  compareProcessStep = (o1: IProcessStep | null, o2: IProcessStep | null): boolean => this.processStepService.compareProcessStep(o1, o2);

  compareTag = (o1: ITag | null, o2: ITag | null): boolean => this.tagService.compareTag(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ company }) => {
      this.company = company;
      if (company) {
        this.updateForm(company);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('atigRecruitingBetaApp.error', { message: err.message })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const company = this.companyFormService.getCompany(this.editForm);
    if (company.id !== null) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(company: ICompany): void {
    this.company = company;
    this.companyFormService.resetForm(this.editForm, company);

    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing<ICategory>(
      this.categoriesSharedCollection,
      company.companyCategory,
      company.category,
    );
    this.subCategoriesSharedCollection = this.subCategoryService.addSubCategoryToCollectionIfMissing<ISubCategory>(
      this.subCategoriesSharedCollection,
      company.companySubCategory,
      company.subCategory,
    );
    this.processesSharedCollection = this.processService.addProcessToCollectionIfMissing<IProcess>(
      this.processesSharedCollection,
      company.companyProcess,
      company.process,
    );
    this.processStepsSharedCollection = this.processStepService.addProcessStepToCollectionIfMissing<IProcessStep>(
      this.processStepsSharedCollection,
      company.companyProcessStep,
      company.processStep,
    );
    this.tagsSharedCollection = this.tagService.addTagToCollectionIfMissing<ITag>(this.tagsSharedCollection, ...(company.tags ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing<ICategory>(
            categories,
            this.company?.companyCategory,
            this.company?.category,
          ),
        ),
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.subCategoryService
      .query()
      .pipe(map((res: HttpResponse<ISubCategory[]>) => res.body ?? []))
      .pipe(
        map((subCategories: ISubCategory[]) =>
          this.subCategoryService.addSubCategoryToCollectionIfMissing<ISubCategory>(
            subCategories,
            this.company?.companySubCategory,
            this.company?.subCategory,
          ),
        ),
      )
      .subscribe((subCategories: ISubCategory[]) => (this.subCategoriesSharedCollection = subCategories));

    this.processService
      .query()
      .pipe(map((res: HttpResponse<IProcess[]>) => res.body ?? []))
      .pipe(
        map((processes: IProcess[]) =>
          this.processService.addProcessToCollectionIfMissing<IProcess>(processes, this.company?.companyProcess, this.company?.process),
        ),
      )
      .subscribe((processes: IProcess[]) => (this.processesSharedCollection = processes));

    this.processStepService
      .query()
      .pipe(map((res: HttpResponse<IProcessStep[]>) => res.body ?? []))
      .pipe(
        map((processSteps: IProcessStep[]) =>
          this.processStepService.addProcessStepToCollectionIfMissing<IProcessStep>(
            processSteps,
            this.company?.companyProcessStep,
            this.company?.processStep,
          ),
        ),
      )
      .subscribe((processSteps: IProcessStep[]) => (this.processStepsSharedCollection = processSteps));

    this.tagService
      .query()
      .pipe(map((res: HttpResponse<ITag[]>) => res.body ?? []))
      .pipe(map((tags: ITag[]) => this.tagService.addTagToCollectionIfMissing<ITag>(tags, ...(this.company?.tags ?? []))))
      .subscribe((tags: ITag[]) => (this.tagsSharedCollection = tags));
  }
}
