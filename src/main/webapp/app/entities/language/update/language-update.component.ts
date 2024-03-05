import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICandidate } from 'app/entities/candidate/candidate.model';
import { CandidateService } from 'app/entities/candidate/service/candidate.service';
import { EnumLanguageName } from 'app/entities/enumerations/enum-language-name.model';
import { EnumLanguageLevel } from 'app/entities/enumerations/enum-language-level.model';
import { LanguageService } from '../service/language.service';
import { ILanguage } from '../language.model';
import { LanguageFormService, LanguageFormGroup } from './language-form.service';

@Component({
  standalone: true,
  selector: 'jhi-language-update',
  templateUrl: './language-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LanguageUpdateComponent implements OnInit {
  isSaving = false;
  language: ILanguage | null = null;
  enumLanguageNameValues = Object.keys(EnumLanguageName);
  enumLanguageLevelValues = Object.keys(EnumLanguageLevel);

  candidatesSharedCollection: ICandidate[] = [];

  editForm: LanguageFormGroup = this.languageFormService.createLanguageFormGroup();

  constructor(
    protected languageService: LanguageService,
    protected languageFormService: LanguageFormService,
    protected candidateService: CandidateService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCandidate = (o1: ICandidate | null, o2: ICandidate | null): boolean => this.candidateService.compareCandidate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ language }) => {
      this.language = language;
      if (language) {
        this.updateForm(language);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const language = this.languageFormService.getLanguage(this.editForm);
    if (language.id !== null) {
      this.subscribeToSaveResponse(this.languageService.update(language));
    } else {
      this.subscribeToSaveResponse(this.languageService.create(language));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILanguage>>): void {
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

  protected updateForm(language: ILanguage): void {
    this.language = language;
    this.languageFormService.resetForm(this.editForm, language);

    this.candidatesSharedCollection = this.candidateService.addCandidateToCollectionIfMissing<ICandidate>(
      this.candidatesSharedCollection,
      language.languageCandidate,
      language.candidate,
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
            this.language?.languageCandidate,
            this.language?.candidate,
          ),
        ),
      )
      .subscribe((candidates: ICandidate[]) => (this.candidatesSharedCollection = candidates));
  }
}
