import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICandidateAdditionalInfos } from 'app/entities/candidate-additional-infos/candidate-additional-infos.model';
import { CandidateAdditionalInfosService } from 'app/entities/candidate-additional-infos/service/candidate-additional-infos.service';
import { INote } from 'app/entities/note/note.model';
import { NoteService } from 'app/entities/note/service/note.service';
import { IEmail } from 'app/entities/email/email.model';
import { EmailService } from 'app/entities/email/service/email.service';
import { IObjectContainingFile } from '../object-containing-file.model';
import { ObjectContainingFileService } from '../service/object-containing-file.service';
import { ObjectContainingFileFormService } from './object-containing-file-form.service';

import { ObjectContainingFileUpdateComponent } from './object-containing-file-update.component';

describe('ObjectContainingFile Management Update Component', () => {
  let comp: ObjectContainingFileUpdateComponent;
  let fixture: ComponentFixture<ObjectContainingFileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let objectContainingFileFormService: ObjectContainingFileFormService;
  let objectContainingFileService: ObjectContainingFileService;
  let candidateAdditionalInfosService: CandidateAdditionalInfosService;
  let noteService: NoteService;
  let emailService: EmailService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ObjectContainingFileUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ObjectContainingFileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ObjectContainingFileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    objectContainingFileFormService = TestBed.inject(ObjectContainingFileFormService);
    objectContainingFileService = TestBed.inject(ObjectContainingFileService);
    candidateAdditionalInfosService = TestBed.inject(CandidateAdditionalInfosService);
    noteService = TestBed.inject(NoteService);
    emailService = TestBed.inject(EmailService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CandidateAdditionalInfos query and add missing value', () => {
      const objectContainingFile: IObjectContainingFile = { id: 456 };
      const candidateDocs: ICandidateAdditionalInfos = { id: 1439 };
      objectContainingFile.candidateDocs = candidateDocs;
      const candidateAdditionalInfos: ICandidateAdditionalInfos = { id: 30881 };
      objectContainingFile.candidateAdditionalInfos = candidateAdditionalInfos;

      const candidateAdditionalInfosCollection: ICandidateAdditionalInfos[] = [{ id: 8992 }];
      jest
        .spyOn(candidateAdditionalInfosService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: candidateAdditionalInfosCollection })));
      const additionalCandidateAdditionalInfos = [candidateDocs, candidateAdditionalInfos];
      const expectedCollection: ICandidateAdditionalInfos[] = [
        ...additionalCandidateAdditionalInfos,
        ...candidateAdditionalInfosCollection,
      ];
      jest.spyOn(candidateAdditionalInfosService, 'addCandidateAdditionalInfosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ objectContainingFile });
      comp.ngOnInit();

      expect(candidateAdditionalInfosService.query).toHaveBeenCalled();
      expect(candidateAdditionalInfosService.addCandidateAdditionalInfosToCollectionIfMissing).toHaveBeenCalledWith(
        candidateAdditionalInfosCollection,
        ...additionalCandidateAdditionalInfos.map(expect.objectContaining),
      );
      expect(comp.candidateAdditionalInfosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Note query and add missing value', () => {
      const objectContainingFile: IObjectContainingFile = { id: 456 };
      const noteDocs: INote = { id: 26145 };
      objectContainingFile.noteDocs = noteDocs;
      const note: INote = { id: 13820 };
      objectContainingFile.note = note;

      const noteCollection: INote[] = [{ id: 28727 }];
      jest.spyOn(noteService, 'query').mockReturnValue(of(new HttpResponse({ body: noteCollection })));
      const additionalNotes = [noteDocs, note];
      const expectedCollection: INote[] = [...additionalNotes, ...noteCollection];
      jest.spyOn(noteService, 'addNoteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ objectContainingFile });
      comp.ngOnInit();

      expect(noteService.query).toHaveBeenCalled();
      expect(noteService.addNoteToCollectionIfMissing).toHaveBeenCalledWith(
        noteCollection,
        ...additionalNotes.map(expect.objectContaining),
      );
      expect(comp.notesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Email query and add missing value', () => {
      const objectContainingFile: IObjectContainingFile = { id: 456 };
      const emailDocs: IEmail = { id: 11038 };
      objectContainingFile.emailDocs = emailDocs;
      const email: IEmail = { id: 7962 };
      objectContainingFile.email = email;

      const emailCollection: IEmail[] = [{ id: 21176 }];
      jest.spyOn(emailService, 'query').mockReturnValue(of(new HttpResponse({ body: emailCollection })));
      const additionalEmails = [emailDocs, email];
      const expectedCollection: IEmail[] = [...additionalEmails, ...emailCollection];
      jest.spyOn(emailService, 'addEmailToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ objectContainingFile });
      comp.ngOnInit();

      expect(emailService.query).toHaveBeenCalled();
      expect(emailService.addEmailToCollectionIfMissing).toHaveBeenCalledWith(
        emailCollection,
        ...additionalEmails.map(expect.objectContaining),
      );
      expect(comp.emailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const objectContainingFile: IObjectContainingFile = { id: 456 };
      const candidateDocs: ICandidateAdditionalInfos = { id: 6682 };
      objectContainingFile.candidateDocs = candidateDocs;
      const candidateAdditionalInfos: ICandidateAdditionalInfos = { id: 30973 };
      objectContainingFile.candidateAdditionalInfos = candidateAdditionalInfos;
      const noteDocs: INote = { id: 19705 };
      objectContainingFile.noteDocs = noteDocs;
      const note: INote = { id: 22985 };
      objectContainingFile.note = note;
      const emailDocs: IEmail = { id: 26255 };
      objectContainingFile.emailDocs = emailDocs;
      const email: IEmail = { id: 31862 };
      objectContainingFile.email = email;

      activatedRoute.data = of({ objectContainingFile });
      comp.ngOnInit();

      expect(comp.candidateAdditionalInfosSharedCollection).toContain(candidateDocs);
      expect(comp.candidateAdditionalInfosSharedCollection).toContain(candidateAdditionalInfos);
      expect(comp.notesSharedCollection).toContain(noteDocs);
      expect(comp.notesSharedCollection).toContain(note);
      expect(comp.emailsSharedCollection).toContain(emailDocs);
      expect(comp.emailsSharedCollection).toContain(email);
      expect(comp.objectContainingFile).toEqual(objectContainingFile);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObjectContainingFile>>();
      const objectContainingFile = { id: 123 };
      jest.spyOn(objectContainingFileFormService, 'getObjectContainingFile').mockReturnValue(objectContainingFile);
      jest.spyOn(objectContainingFileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ objectContainingFile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: objectContainingFile }));
      saveSubject.complete();

      // THEN
      expect(objectContainingFileFormService.getObjectContainingFile).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(objectContainingFileService.update).toHaveBeenCalledWith(expect.objectContaining(objectContainingFile));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObjectContainingFile>>();
      const objectContainingFile = { id: 123 };
      jest.spyOn(objectContainingFileFormService, 'getObjectContainingFile').mockReturnValue({ id: null });
      jest.spyOn(objectContainingFileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ objectContainingFile: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: objectContainingFile }));
      saveSubject.complete();

      // THEN
      expect(objectContainingFileFormService.getObjectContainingFile).toHaveBeenCalled();
      expect(objectContainingFileService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObjectContainingFile>>();
      const objectContainingFile = { id: 123 };
      jest.spyOn(objectContainingFileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ objectContainingFile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(objectContainingFileService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCandidateAdditionalInfos', () => {
      it('Should forward to candidateAdditionalInfosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(candidateAdditionalInfosService, 'compareCandidateAdditionalInfos');
        comp.compareCandidateAdditionalInfos(entity, entity2);
        expect(candidateAdditionalInfosService.compareCandidateAdditionalInfos).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNote', () => {
      it('Should forward to noteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(noteService, 'compareNote');
        comp.compareNote(entity, entity2);
        expect(noteService.compareNote).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmail', () => {
      it('Should forward to emailService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(emailService, 'compareEmail');
        comp.compareEmail(entity, entity2);
        expect(emailService.compareEmail).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
