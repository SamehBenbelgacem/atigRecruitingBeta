import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { IDesider } from '../desider.model';
import { DesiderService } from '../service/desider.service';
import { DesiderFormService, DesiderFormGroup } from './desider-form.service';

@Component({
  standalone: true,
  selector: 'jhi-desider-update',
  templateUrl: './desider-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DesiderUpdateComponent implements OnInit {
  isSaving = false;
  desider: IDesider | null = null;

  companiesSharedCollection: ICompany[] = [];

  editForm: DesiderFormGroup = this.desiderFormService.createDesiderFormGroup();

  constructor(
    protected desiderService: DesiderService,
    protected desiderFormService: DesiderFormService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ desider }) => {
      this.desider = desider;
      if (desider) {
        this.updateForm(desider);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const desider = this.desiderFormService.getDesider(this.editForm);
    if (desider.id !== null) {
      this.subscribeToSaveResponse(this.desiderService.update(desider));
    } else {
      this.subscribeToSaveResponse(this.desiderService.create(desider));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDesider>>): void {
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

  protected updateForm(desider: IDesider): void {
    this.desider = desider;
    this.desiderFormService.resetForm(this.editForm, desider);

    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      desider.desiderCompany,
      desider.company,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) =>
          this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.desider?.desiderCompany, this.desider?.company),
        ),
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }
}
