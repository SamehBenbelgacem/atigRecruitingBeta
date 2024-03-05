import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICandidateAdditionalInfos } from '../candidate-additional-infos.model';
import { CandidateAdditionalInfosService } from '../service/candidate-additional-infos.service';
import { CandidateAdditionalInfosFormService, CandidateAdditionalInfosFormGroup } from './candidate-additional-infos-form.service';

@Component({
  standalone: true,
  selector: 'jhi-candidate-additional-infos-update',
  templateUrl: './candidate-additional-infos-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CandidateAdditionalInfosUpdateComponent implements OnInit {
  isSaving = false;
  candidateAdditionalInfos: ICandidateAdditionalInfos | null = null;

  editForm: CandidateAdditionalInfosFormGroup = this.candidateAdditionalInfosFormService.createCandidateAdditionalInfosFormGroup();

  constructor(
    protected candidateAdditionalInfosService: CandidateAdditionalInfosService,
    protected candidateAdditionalInfosFormService: CandidateAdditionalInfosFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidateAdditionalInfos }) => {
      this.candidateAdditionalInfos = candidateAdditionalInfos;
      if (candidateAdditionalInfos) {
        this.updateForm(candidateAdditionalInfos);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidateAdditionalInfos = this.candidateAdditionalInfosFormService.getCandidateAdditionalInfos(this.editForm);
    if (candidateAdditionalInfos.id !== null) {
      this.subscribeToSaveResponse(this.candidateAdditionalInfosService.update(candidateAdditionalInfos));
    } else {
      this.subscribeToSaveResponse(this.candidateAdditionalInfosService.create(candidateAdditionalInfos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidateAdditionalInfos>>): void {
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

  protected updateForm(candidateAdditionalInfos: ICandidateAdditionalInfos): void {
    this.candidateAdditionalInfos = candidateAdditionalInfos;
    this.candidateAdditionalInfosFormService.resetForm(this.editForm, candidateAdditionalInfos);
  }
}
