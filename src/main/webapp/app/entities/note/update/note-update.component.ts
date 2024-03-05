import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { ICandidate } from 'app/entities/candidate/candidate.model';
import { CandidateService } from 'app/entities/candidate/service/candidate.service';
import { NoteService } from '../service/note.service';
import { INote } from '../note.model';
import { NoteFormService, NoteFormGroup } from './note-form.service';

@Component({
  standalone: true,
  selector: 'jhi-note-update',
  templateUrl: './note-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class NoteUpdateComponent implements OnInit {
  isSaving = false;
  note: INote | null = null;

  companiesSharedCollection: ICompany[] = [];
  candidatesSharedCollection: ICandidate[] = [];

  editForm: NoteFormGroup = this.noteFormService.createNoteFormGroup();

  constructor(
    protected noteService: NoteService,
    protected noteFormService: NoteFormService,
    protected companyService: CompanyService,
    protected candidateService: CandidateService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  compareCandidate = (o1: ICandidate | null, o2: ICandidate | null): boolean => this.candidateService.compareCandidate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ note }) => {
      this.note = note;
      if (note) {
        this.updateForm(note);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const note = this.noteFormService.getNote(this.editForm);
    if (note.id !== null) {
      this.subscribeToSaveResponse(this.noteService.update(note));
    } else {
      this.subscribeToSaveResponse(this.noteService.create(note));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INote>>): void {
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

  protected updateForm(note: INote): void {
    this.note = note;
    this.noteFormService.resetForm(this.editForm, note);

    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      note.noteCompany,
      note.company,
    );
    this.candidatesSharedCollection = this.candidateService.addCandidateToCollectionIfMissing<ICandidate>(
      this.candidatesSharedCollection,
      note.noteCandidate,
      note.candidate,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) =>
          this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.note?.noteCompany, this.note?.company),
        ),
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));

    this.candidateService
      .query()
      .pipe(map((res: HttpResponse<ICandidate[]>) => res.body ?? []))
      .pipe(
        map((candidates: ICandidate[]) =>
          this.candidateService.addCandidateToCollectionIfMissing<ICandidate>(candidates, this.note?.noteCandidate, this.note?.candidate),
        ),
      )
      .subscribe((candidates: ICandidate[]) => (this.candidatesSharedCollection = candidates));
  }
}
