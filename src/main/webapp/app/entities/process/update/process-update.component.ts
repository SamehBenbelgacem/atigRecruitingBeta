import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EnumProsessType } from 'app/entities/enumerations/enum-prosess-type.model';
import { IProcess } from '../process.model';
import { ProcessService } from '../service/process.service';
import { ProcessFormService, ProcessFormGroup } from './process-form.service';

@Component({
  standalone: true,
  selector: 'jhi-process-update',
  templateUrl: './process-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProcessUpdateComponent implements OnInit {
  isSaving = false;
  process: IProcess | null = null;
  enumProsessTypeValues = Object.keys(EnumProsessType);

  editForm: ProcessFormGroup = this.processFormService.createProcessFormGroup();

  constructor(
    protected processService: ProcessService,
    protected processFormService: ProcessFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ process }) => {
      this.process = process;
      if (process) {
        this.updateForm(process);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const process = this.processFormService.getProcess(this.editForm);
    if (process.id !== null) {
      this.subscribeToSaveResponse(this.processService.update(process));
    } else {
      this.subscribeToSaveResponse(this.processService.create(process));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcess>>): void {
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

  protected updateForm(process: IProcess): void {
    this.process = process;
    this.processFormService.resetForm(this.editForm, process);
  }
}
