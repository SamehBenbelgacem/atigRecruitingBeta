import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmailcredentials } from '../emailcredentials.model';
import { EmailcredentialsService } from '../service/emailcredentials.service';
import { EmailcredentialsFormService, EmailcredentialsFormGroup } from './emailcredentials-form.service';

@Component({
  standalone: true,
  selector: 'jhi-emailcredentials-update',
  templateUrl: './emailcredentials-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmailcredentialsUpdateComponent implements OnInit {
  isSaving = false;
  emailcredentials: IEmailcredentials | null = null;

  editForm: EmailcredentialsFormGroup = this.emailcredentialsFormService.createEmailcredentialsFormGroup();

  constructor(
    protected emailcredentialsService: EmailcredentialsService,
    protected emailcredentialsFormService: EmailcredentialsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailcredentials }) => {
      this.emailcredentials = emailcredentials;
      if (emailcredentials) {
        this.updateForm(emailcredentials);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emailcredentials = this.emailcredentialsFormService.getEmailcredentials(this.editForm);
    if (emailcredentials.id !== null) {
      this.subscribeToSaveResponse(this.emailcredentialsService.update(emailcredentials));
    } else {
      this.subscribeToSaveResponse(this.emailcredentialsService.create(emailcredentials));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmailcredentials>>): void {
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

  protected updateForm(emailcredentials: IEmailcredentials): void {
    this.emailcredentials = emailcredentials;
    this.emailcredentialsFormService.resetForm(this.editForm, emailcredentials);
  }
}
