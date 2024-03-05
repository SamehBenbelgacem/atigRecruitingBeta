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
import { IEmailcredentials } from 'app/entities/emailcredentials/emailcredentials.model';
import { EmailcredentialsService } from 'app/entities/emailcredentials/service/emailcredentials.service';
import { ICandidate } from 'app/entities/candidate/candidate.model';
import { CandidateService } from 'app/entities/candidate/service/candidate.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { EnumEmailType } from 'app/entities/enumerations/enum-email-type.model';
import { EmailService } from '../service/email.service';
import { IEmail } from '../email.model';
import { EmailFormService, EmailFormGroup } from './email-form.service';

@Component({
  standalone: true,
  selector: 'jhi-email-update',
  templateUrl: './email-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmailUpdateComponent implements OnInit {
  isSaving = false;
  email: IEmail | null = null;
  enumEmailTypeValues = Object.keys(EnumEmailType);

  emailcredentialsSharedCollection: IEmailcredentials[] = [];
  candidatesSharedCollection: ICandidate[] = [];
  companiesSharedCollection: ICompany[] = [];

  editForm: EmailFormGroup = this.emailFormService.createEmailFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected emailService: EmailService,
    protected emailFormService: EmailFormService,
    protected emailcredentialsService: EmailcredentialsService,
    protected candidateService: CandidateService,
    protected companyService: CompanyService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEmailcredentials = (o1: IEmailcredentials | null, o2: IEmailcredentials | null): boolean =>
    this.emailcredentialsService.compareEmailcredentials(o1, o2);

  compareCandidate = (o1: ICandidate | null, o2: ICandidate | null): boolean => this.candidateService.compareCandidate(o1, o2);

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ email }) => {
      this.email = email;
      if (email) {
        this.updateForm(email);
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
    const email = this.emailFormService.getEmail(this.editForm);
    if (email.id !== null) {
      this.subscribeToSaveResponse(this.emailService.update(email));
    } else {
      this.subscribeToSaveResponse(this.emailService.create(email));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmail>>): void {
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

  protected updateForm(email: IEmail): void {
    this.email = email;
    this.emailFormService.resetForm(this.editForm, email);

    this.emailcredentialsSharedCollection = this.emailcredentialsService.addEmailcredentialsToCollectionIfMissing<IEmailcredentials>(
      this.emailcredentialsSharedCollection,
      email.emailEmailcredentials,
      email.emailcredentials,
    );
    this.candidatesSharedCollection = this.candidateService.addCandidateToCollectionIfMissing<ICandidate>(
      this.candidatesSharedCollection,
      email.emailCandidate,
      email.candidate,
    );
    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      email.emailCompany,
      email.company,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.emailcredentialsService
      .query()
      .pipe(map((res: HttpResponse<IEmailcredentials[]>) => res.body ?? []))
      .pipe(
        map((emailcredentials: IEmailcredentials[]) =>
          this.emailcredentialsService.addEmailcredentialsToCollectionIfMissing<IEmailcredentials>(
            emailcredentials,
            this.email?.emailEmailcredentials,
            this.email?.emailcredentials,
          ),
        ),
      )
      .subscribe((emailcredentials: IEmailcredentials[]) => (this.emailcredentialsSharedCollection = emailcredentials));

    this.candidateService
      .query()
      .pipe(map((res: HttpResponse<ICandidate[]>) => res.body ?? []))
      .pipe(
        map((candidates: ICandidate[]) =>
          this.candidateService.addCandidateToCollectionIfMissing<ICandidate>(
            candidates,
            this.email?.emailCandidate,
            this.email?.candidate,
          ),
        ),
      )
      .subscribe((candidates: ICandidate[]) => (this.candidatesSharedCollection = candidates));

    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) =>
          this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.email?.emailCompany, this.email?.company),
        ),
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }
}
