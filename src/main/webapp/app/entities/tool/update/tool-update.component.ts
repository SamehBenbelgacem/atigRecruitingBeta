import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IExperience } from 'app/entities/experience/experience.model';
import { ExperienceService } from 'app/entities/experience/service/experience.service';
import { ITool } from '../tool.model';
import { ToolService } from '../service/tool.service';
import { ToolFormService, ToolFormGroup } from './tool-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tool-update',
  templateUrl: './tool-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ToolUpdateComponent implements OnInit {
  isSaving = false;
  tool: ITool | null = null;

  experiencesSharedCollection: IExperience[] = [];

  editForm: ToolFormGroup = this.toolFormService.createToolFormGroup();

  constructor(
    protected toolService: ToolService,
    protected toolFormService: ToolFormService,
    protected experienceService: ExperienceService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareExperience = (o1: IExperience | null, o2: IExperience | null): boolean => this.experienceService.compareExperience(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tool }) => {
      this.tool = tool;
      if (tool) {
        this.updateForm(tool);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tool = this.toolFormService.getTool(this.editForm);
    if (tool.id !== null) {
      this.subscribeToSaveResponse(this.toolService.update(tool));
    } else {
      this.subscribeToSaveResponse(this.toolService.create(tool));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITool>>): void {
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

  protected updateForm(tool: ITool): void {
    this.tool = tool;
    this.toolFormService.resetForm(this.editForm, tool);

    this.experiencesSharedCollection = this.experienceService.addExperienceToCollectionIfMissing<IExperience>(
      this.experiencesSharedCollection,
      tool.toolExperience,
      tool.experience,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.experienceService
      .query()
      .pipe(map((res: HttpResponse<IExperience[]>) => res.body ?? []))
      .pipe(
        map((experiences: IExperience[]) =>
          this.experienceService.addExperienceToCollectionIfMissing<IExperience>(
            experiences,
            this.tool?.toolExperience,
            this.tool?.experience,
          ),
        ),
      )
      .subscribe((experiences: IExperience[]) => (this.experiencesSharedCollection = experiences));
  }
}
