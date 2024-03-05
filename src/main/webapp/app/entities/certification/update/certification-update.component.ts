import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICandidate } from 'app/entities/candidate/candidate.model';
import { CandidateService } from 'app/entities/candidate/service/candidate.service';
import { ICertification } from '../certification.model';
import { CertificationService } from '../service/certification.service';
import { CertificationFormService, CertificationFormGroup } from './certification-form.service';

@Component({
  standalone: true,
  selector: 'jhi-certification-update',
  templateUrl: './certification-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CertificationUpdateComponent implements OnInit {
  isSaving = false;
  certification: ICertification | null = null;

  candidatesSharedCollection: ICandidate[] = [];

  editForm: CertificationFormGroup = this.certificationFormService.createCertificationFormGroup();

  constructor(
    protected certificationService: CertificationService,
    protected certificationFormService: CertificationFormService,
    protected candidateService: CandidateService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCandidate = (o1: ICandidate | null, o2: ICandidate | null): boolean => this.candidateService.compareCandidate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ certification }) => {
      this.certification = certification;
      if (certification) {
        this.updateForm(certification);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const certification = this.certificationFormService.getCertification(this.editForm);
    if (certification.id !== null) {
      this.subscribeToSaveResponse(this.certificationService.update(certification));
    } else {
      this.subscribeToSaveResponse(this.certificationService.create(certification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICertification>>): void {
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

  protected updateForm(certification: ICertification): void {
    this.certification = certification;
    this.certificationFormService.resetForm(this.editForm, certification);

    this.candidatesSharedCollection = this.candidateService.addCandidateToCollectionIfMissing<ICandidate>(
      this.candidatesSharedCollection,
      certification.certificationCandidate,
      certification.candidate,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.candidateService
      .query()
      .pipe(map((res: HttpResponse<ICandidate[]>) => res.body ?? []))
      .pipe(
        map((candidates: ICandidate[]) =>
          this.candidateService.addCandidateToCollectionIfMissing<ICandidate>(
            candidates,
            this.certification?.certificationCandidate,
            this.certification?.candidate,
          ),
        ),
      )
      .subscribe((candidates: ICandidate[]) => (this.candidatesSharedCollection = candidates));
  }
}
