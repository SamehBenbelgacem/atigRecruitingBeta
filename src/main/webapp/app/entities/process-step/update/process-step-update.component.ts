import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProcess } from 'app/entities/process/process.model';
import { ProcessService } from 'app/entities/process/service/process.service';
import { EnumPriority } from 'app/entities/enumerations/enum-priority.model';
import { ProcessStepService } from '../service/process-step.service';
import { IProcessStep } from '../process-step.model';
import { ProcessStepFormService, ProcessStepFormGroup } from './process-step-form.service';

@Component({
  standalone: true,
  selector: 'jhi-process-step-update',
  templateUrl: './process-step-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProcessStepUpdateComponent implements OnInit {
  isSaving = false;
  processStep: IProcessStep | null = null;
  enumPriorityValues = Object.keys(EnumPriority);

  processesSharedCollection: IProcess[] = [];

  editForm: ProcessStepFormGroup = this.processStepFormService.createProcessStepFormGroup();

  constructor(
    protected processStepService: ProcessStepService,
    protected processStepFormService: ProcessStepFormService,
    protected processService: ProcessService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareProcess = (o1: IProcess | null, o2: IProcess | null): boolean => this.processService.compareProcess(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processStep }) => {
      this.processStep = processStep;
      if (processStep) {
        this.updateForm(processStep);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const processStep = this.processStepFormService.getProcessStep(this.editForm);
    if (processStep.id !== null) {
      this.subscribeToSaveResponse(this.processStepService.update(processStep));
    } else {
      this.subscribeToSaveResponse(this.processStepService.create(processStep));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessStep>>): void {
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

  protected updateForm(processStep: IProcessStep): void {
    this.processStep = processStep;
    this.processStepFormService.resetForm(this.editForm, processStep);

    this.processesSharedCollection = this.processService.addProcessToCollectionIfMissing<IProcess>(
      this.processesSharedCollection,
      processStep.processStepProcess,
      processStep.process,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.processService
      .query()
      .pipe(map((res: HttpResponse<IProcess[]>) => res.body ?? []))
      .pipe(
        map((processes: IProcess[]) =>
          this.processService.addProcessToCollectionIfMissing<IProcess>(
            processes,
            this.processStep?.processStepProcess,
            this.processStep?.process,
          ),
        ),
      )
      .subscribe((processes: IProcess[]) => (this.processesSharedCollection = processes));
  }
}
