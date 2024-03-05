import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEmail } from 'app/entities/email/email.model';
import { EmailService } from 'app/entities/email/service/email.service';
import { SubEmailService } from '../service/sub-email.service';
import { ISubEmail } from '../sub-email.model';
import { SubEmailFormService } from './sub-email-form.service';

import { SubEmailUpdateComponent } from './sub-email-update.component';

describe('SubEmail Management Update Component', () => {
  let comp: SubEmailUpdateComponent;
  let fixture: ComponentFixture<SubEmailUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subEmailFormService: SubEmailFormService;
  let subEmailService: SubEmailService;
  let emailService: EmailService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SubEmailUpdateComponent],
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
      .overrideTemplate(SubEmailUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubEmailUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subEmailFormService = TestBed.inject(SubEmailFormService);
    subEmailService = TestBed.inject(SubEmailService);
    emailService = TestBed.inject(EmailService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Email query and add missing value', () => {
      const subEmail: ISubEmail = { id: 456 };
      const subEmailEmail: IEmail = { id: 29313 };
      subEmail.subEmailEmail = subEmailEmail;
      const email: IEmail = { id: 11440 };
      subEmail.email = email;

      const emailCollection: IEmail[] = [{ id: 4558 }];
      jest.spyOn(emailService, 'query').mockReturnValue(of(new HttpResponse({ body: emailCollection })));
      const additionalEmails = [subEmailEmail, email];
      const expectedCollection: IEmail[] = [...additionalEmails, ...emailCollection];
      jest.spyOn(emailService, 'addEmailToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subEmail });
      comp.ngOnInit();

      expect(emailService.query).toHaveBeenCalled();
      expect(emailService.addEmailToCollectionIfMissing).toHaveBeenCalledWith(
        emailCollection,
        ...additionalEmails.map(expect.objectContaining),
      );
      expect(comp.emailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subEmail: ISubEmail = { id: 456 };
      const subEmailEmail: IEmail = { id: 5708 };
      subEmail.subEmailEmail = subEmailEmail;
      const email: IEmail = { id: 15959 };
      subEmail.email = email;

      activatedRoute.data = of({ subEmail });
      comp.ngOnInit();

      expect(comp.emailsSharedCollection).toContain(subEmailEmail);
      expect(comp.emailsSharedCollection).toContain(email);
      expect(comp.subEmail).toEqual(subEmail);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubEmail>>();
      const subEmail = { id: 123 };
      jest.spyOn(subEmailFormService, 'getSubEmail').mockReturnValue(subEmail);
      jest.spyOn(subEmailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subEmail });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subEmail }));
      saveSubject.complete();

      // THEN
      expect(subEmailFormService.getSubEmail).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(subEmailService.update).toHaveBeenCalledWith(expect.objectContaining(subEmail));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubEmail>>();
      const subEmail = { id: 123 };
      jest.spyOn(subEmailFormService, 'getSubEmail').mockReturnValue({ id: null });
      jest.spyOn(subEmailService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subEmail: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subEmail }));
      saveSubject.complete();

      // THEN
      expect(subEmailFormService.getSubEmail).toHaveBeenCalled();
      expect(subEmailService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubEmail>>();
      const subEmail = { id: 123 };
      jest.spyOn(subEmailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subEmail });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subEmailService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
