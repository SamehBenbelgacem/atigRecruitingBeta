import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { ITag } from 'app/entities/tag/tag.model';
import { TagService } from 'app/entities/tag/service/tag.service';
import { OfferService } from '../service/offer.service';
import { IOffer } from '../offer.model';
import { OfferFormService, OfferFormGroup } from './offer-form.service';

@Component({
  standalone: true,
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OfferUpdateComponent implements OnInit {
  isSaving = false;
  offer: IOffer | null = null;

  companiesSharedCollection: ICompany[] = [];
  tagsSharedCollection: ITag[] = [];

  editForm: OfferFormGroup = this.offerFormService.createOfferFormGroup();

  constructor(
    protected offerService: OfferService,
    protected offerFormService: OfferFormService,
    protected companyService: CompanyService,
    protected tagService: TagService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  compareTag = (o1: ITag | null, o2: ITag | null): boolean => this.tagService.compareTag(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offer }) => {
      this.offer = offer;
      if (offer) {
        this.updateForm(offer);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offer = this.offerFormService.getOffer(this.editForm);
    if (offer.id !== null) {
      this.subscribeToSaveResponse(this.offerService.update(offer));
    } else {
      this.subscribeToSaveResponse(this.offerService.create(offer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffer>>): void {
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

  protected updateForm(offer: IOffer): void {
    this.offer = offer;
    this.offerFormService.resetForm(this.editForm, offer);

    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      offer.offerCompany,
      offer.company,
    );
    this.tagsSharedCollection = this.tagService.addTagToCollectionIfMissing<ITag>(this.tagsSharedCollection, ...(offer.tags ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) =>
          this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.offer?.offerCompany, this.offer?.company),
        ),
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));

    this.tagService
      .query()
      .pipe(map((res: HttpResponse<ITag[]>) => res.body ?? []))
      .pipe(map((tags: ITag[]) => this.tagService.addTagToCollectionIfMissing<ITag>(tags, ...(this.offer?.tags ?? []))))
      .subscribe((tags: ITag[]) => (this.tagsSharedCollection = tags));
  }
}
