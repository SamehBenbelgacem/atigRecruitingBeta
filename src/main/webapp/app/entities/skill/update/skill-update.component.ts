import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICandidate } from 'app/entities/candidate/candidate.model';
import { CandidateService } from 'app/entities/candidate/service/candidate.service';
import { ISkill } from '../skill.model';
import { SkillService } from '../service/skill.service';
import { SkillFormService, SkillFormGroup } from './skill-form.service';

@Component({
  standalone: true,
  selector: 'jhi-skill-update',
  templateUrl: './skill-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SkillUpdateComponent implements OnInit {
  isSaving = false;
  skill: ISkill | null = null;

  candidatesSharedCollection: ICandidate[] = [];

  editForm: SkillFormGroup = this.skillFormService.createSkillFormGroup();

  constructor(
    protected skillService: SkillService,
    protected skillFormService: SkillFormService,
    protected candidateService: CandidateService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCandidate = (o1: ICandidate | null, o2: ICandidate | null): boolean => this.candidateService.compareCandidate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ skill }) => {
      this.skill = skill;
      if (skill) {
        this.updateForm(skill);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const skill = this.skillFormService.getSkill(this.editForm);
    if (skill.id !== null) {
      this.subscribeToSaveResponse(this.skillService.update(skill));
    } else {
      this.subscribeToSaveResponse(this.skillService.create(skill));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkill>>): void {
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

  protected updateForm(skill: ISkill): void {
    this.skill = skill;
    this.skillFormService.resetForm(this.editForm, skill);

    this.candidatesSharedCollection = this.candidateService.addCandidateToCollectionIfMissing<ICandidate>(
      this.candidatesSharedCollection,
      skill.skillCandidate,
      skill.candidate,
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
            this.skill?.skillCandidate,
            this.skill?.candidate,
          ),
        ),
      )
      .subscribe((candidates: ICandidate[]) => (this.candidatesSharedCollection = candidates));
  }
}
