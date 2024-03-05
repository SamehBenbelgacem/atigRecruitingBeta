import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEmail } from 'app/entities/email/email.model';
import { EmailService } from 'app/entities/email/service/email.service';
import { EnumEmailType } from 'app/entities/enumerations/enum-email-type.model';
import { SubEmailService } from '../service/sub-email.service';
import { ISubEmail } from '../sub-email.model';
import { SubEmailFormService, SubEmailFormGroup } from './sub-email-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sub-email-update',
  templateUrl: './sub-email-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SubEmailUpdateComponent implements OnInit {
  isSaving = false;
  subEmail: ISubEmail | null = null;
  enumEmailTypeValues = Object.keys(EnumEmailType);

  emailsSharedCollection: IEmail[] = [];

  editForm: SubEmailFormGroup = this.subEmailFormService.createSubEmailFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected subEmailService: SubEmailService,
    protected subEmailFormService: SubEmailFormService,
    protected emailService: EmailService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEmail = (o1: IEmail | null, o2: IEmail | null): boolean => this.emailService.compareEmail(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subEmail }) => {
      this.subEmail = subEmail;
      if (subEmail) {
        this.updateForm(subEmail);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('atigRecruitingBetaApp.error', { message: err.message })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subEmail = this.subEmailFormService.getSubEmail(this.editForm);
    if (subEmail.id !== null) {
      this.subscribeToSaveResponse(this.subEmailService.update(subEmail));
    } else {
      this.subscribeToSaveResponse(this.subEmailService.create(subEmail));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubEmail>>): void {
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

  protected updateForm(subEmail: ISubEmail): void {
    this.subEmail = subEmail;
    this.subEmailFormService.resetForm(this.editForm, subEmail);

    this.emailsSharedCollection = this.emailService.addEmailToCollectionIfMissing<IEmail>(
      this.emailsSharedCollection,
      subEmail.subEmailEmail,
      subEmail.email,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.emailService
      .query()
      .pipe(map((res: HttpResponse<IEmail[]>) => res.body ?? []))
      .pipe(
        map((emails: IEmail[]) =>
          this.emailService.addEmailToCollectionIfMissing<IEmail>(emails, this.subEmail?.subEmailEmail, this.subEmail?.email),
        ),
      )
      .subscribe((emails: IEmail[]) => (this.emailsSharedCollection = emails));
  }
}
