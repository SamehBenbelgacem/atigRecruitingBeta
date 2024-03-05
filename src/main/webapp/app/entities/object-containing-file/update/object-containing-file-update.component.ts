import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICandidateAdditionalInfos } from 'app/entities/candidate-additional-infos/candidate-additional-infos.model';
import { CandidateAdditionalInfosService } from 'app/entities/candidate-additional-infos/service/candidate-additional-infos.service';
import { INote } from 'app/entities/note/note.model';
import { NoteService } from 'app/entities/note/service/note.service';
import { IEmail } from 'app/entities/email/email.model';
import { EmailService } from 'app/entities/email/service/email.service';
import { ObjectContainingFileService } from '../service/object-containing-file.service';
import { IObjectContainingFile } from '../object-containing-file.model';
import { ObjectContainingFileFormService, ObjectContainingFileFormGroup } from './object-containing-file-form.service';

@Component({
  standalone: true,
  selector: 'jhi-object-containing-file-update',
  templateUrl: './object-containing-file-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ObjectContainingFileUpdateComponent implements OnInit {
  isSaving = false;
  objectContainingFile: IObjectContainingFile | null = null;

  candidateAdditionalInfosSharedCollection: ICandidateAdditionalInfos[] = [];
  notesSharedCollection: INote[] = [];
  emailsSharedCollection: IEmail[] = [];

  editForm: ObjectContainingFileFormGroup = this.objectContainingFileFormService.createObjectContainingFileFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected objectContainingFileService: ObjectContainingFileService,
    protected objectContainingFileFormService: ObjectContainingFileFormService,
    protected candidateAdditionalInfosService: CandidateAdditionalInfosService,
    protected noteService: NoteService,
    protected emailService: EmailService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCandidateAdditionalInfos = (o1: ICandidateAdditionalInfos | null, o2: ICandidateAdditionalInfos | null): boolean =>
    this.candidateAdditionalInfosService.compareCandidateAdditionalInfos(o1, o2);

  compareNote = (o1: INote | null, o2: INote | null): boolean => this.noteService.compareNote(o1, o2);

  compareEmail = (o1: IEmail | null, o2: IEmail | null): boolean => this.emailService.compareEmail(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ objectContainingFile }) => {
      this.objectContainingFile = objectContainingFile;
      if (objectContainingFile) {
        this.updateForm(objectContainingFile);
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const objectContainingFile = this.objectContainingFileFormService.getObjectContainingFile(this.editForm);
    if (objectContainingFile.id !== null) {
      this.subscribeToSaveResponse(this.objectContainingFileService.update(objectContainingFile));
    } else {
      this.subscribeToSaveResponse(this.objectContainingFileService.create(objectContainingFile));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObjectContainingFile>>): void {
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

  protected updateForm(objectContainingFile: IObjectContainingFile): void {
    this.objectContainingFile = objectContainingFile;
    this.objectContainingFileFormService.resetForm(this.editForm, objectContainingFile);

    this.candidateAdditionalInfosSharedCollection =
      this.candidateAdditionalInfosService.addCandidateAdditionalInfosToCollectionIfMissing<ICandidateAdditionalInfos>(
        this.candidateAdditionalInfosSharedCollection,
        objectContainingFile.candidateDocs,
        objectContainingFile.candidateAdditionalInfos,
      );
    this.notesSharedCollection = this.noteService.addNoteToCollectionIfMissing<INote>(
      this.notesSharedCollection,
      objectContainingFile.noteDocs,
      objectContainingFile.note,
    );
    this.emailsSharedCollection = this.emailService.addEmailToCollectionIfMissing<IEmail>(
      this.emailsSharedCollection,
      objectContainingFile.emailDocs,
      objectContainingFile.email,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.candidateAdditionalInfosService
      .query()
      .pipe(map((res: HttpResponse<ICandidateAdditionalInfos[]>) => res.body ?? []))
      .pipe(
        map((candidateAdditionalInfos: ICandidateAdditionalInfos[]) =>
          this.candidateAdditionalInfosService.addCandidateAdditionalInfosToCollectionIfMissing<ICandidateAdditionalInfos>(
            candidateAdditionalInfos,
            this.objectContainingFile?.candidateDocs,
            this.objectContainingFile?.candidateAdditionalInfos,
          ),
        ),
      )
      .subscribe(
        (candidateAdditionalInfos: ICandidateAdditionalInfos[]) =>
          (this.candidateAdditionalInfosSharedCollection = candidateAdditionalInfos),
      );

    this.noteService
      .query()
      .pipe(map((res: HttpResponse<INote[]>) => res.body ?? []))
      .pipe(
        map((notes: INote[]) =>
          this.noteService.addNoteToCollectionIfMissing<INote>(notes, this.objectContainingFile?.noteDocs, this.objectContainingFile?.note),
        ),
      )
      .subscribe((notes: INote[]) => (this.notesSharedCollection = notes));

    this.emailService
      .query()
      .pipe(map((res: HttpResponse<IEmail[]>) => res.body ?? []))
      .pipe(
        map((emails: IEmail[]) =>
          this.emailService.addEmailToCollectionIfMissing<IEmail>(
            emails,
            this.objectContainingFile?.emailDocs,
            this.objectContainingFile?.email,
          ),
        ),
      )
      .subscribe((emails: IEmail[]) => (this.emailsSharedCollection = emails));
  }
}
