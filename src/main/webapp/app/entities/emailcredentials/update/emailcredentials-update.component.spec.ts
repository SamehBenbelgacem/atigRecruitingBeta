import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmailcredentialsService } from '../service/emailcredentials.service';
import { IEmailcredentials } from '../emailcredentials.model';
import { EmailcredentialsFormService } from './emailcredentials-form.service';

import { EmailcredentialsUpdateComponent } from './emailcredentials-update.component';

describe('Emailcredentials Management Update Component', () => {
  let comp: EmailcredentialsUpdateComponent;
  let fixture: ComponentFixture<EmailcredentialsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emailcredentialsFormService: EmailcredentialsFormService;
  let emailcredentialsService: EmailcredentialsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EmailcredentialsUpdateComponent],
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
      .overrideTemplate(EmailcredentialsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailcredentialsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emailcredentialsFormService = TestBed.inject(EmailcredentialsFormService);
    emailcredentialsService = TestBed.inject(EmailcredentialsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const emailcredentials: IEmailcredentials = { id: 456 };

      activatedRoute.data = of({ emailcredentials });
      comp.ngOnInit();

      expect(comp.emailcredentials).toEqual(emailcredentials);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailcredentials>>();
      const emailcredentials = { id: 123 };
      jest.spyOn(emailcredentialsFormService, 'getEmailcredentials').mockReturnValue(emailcredentials);
      jest.spyOn(emailcredentialsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailcredentials });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailcredentials }));
      saveSubject.complete();

      // THEN
      expect(emailcredentialsFormService.getEmailcredentials).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(emailcredentialsService.update).toHaveBeenCalledWith(expect.objectContaining(emailcredentials));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailcredentials>>();
      const emailcredentials = { id: 123 };
      jest.spyOn(emailcredentialsFormService, 'getEmailcredentials').mockReturnValue({ id: null });
      jest.spyOn(emailcredentialsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailcredentials: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailcredentials }));
      saveSubject.complete();

      // THEN
      expect(emailcredentialsFormService.getEmailcredentials).toHaveBeenCalled();
      expect(emailcredentialsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailcredentials>>();
      const emailcredentials = { id: 123 };
      jest.spyOn(emailcredentialsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailcredentials });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emailcredentialsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
