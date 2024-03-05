import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEmailcredentials } from 'app/entities/emailcredentials/emailcredentials.model';
import { EmailcredentialsService } from 'app/entities/emailcredentials/service/emailcredentials.service';
import { ICandidate } from 'app/entities/candidate/candidate.model';
import { CandidateService } from 'app/entities/candidate/service/candidate.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { IEmail } from '../email.model';
import { EmailService } from '../service/email.service';
import { EmailFormService } from './email-form.service';

import { EmailUpdateComponent } from './email-update.component';

describe('Email Management Update Component', () => {
  let comp: EmailUpdateComponent;
  let fixture: ComponentFixture<EmailUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emailFormService: EmailFormService;
  let emailService: EmailService;
  let emailcredentialsService: EmailcredentialsService;
  let candidateService: CandidateService;
  let companyService: CompanyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EmailUpdateComponent],
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
      .overrideTemplate(EmailUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emailFormService = TestBed.inject(EmailFormService);
    emailService = TestBed.inject(EmailService);
    emailcredentialsService = TestBed.inject(EmailcredentialsService);
    candidateService = TestBed.inject(CandidateService);
    companyService = TestBed.inject(CompanyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Emailcredentials query and add missing value', () => {
      const email: IEmail = { id: 456 };
      const emailEmailcredentials: IEmailcredentials = { id: 10539 };
      email.emailEmailcredentials = emailEmailcredentials;
      const emailcredentials: IEmailcredentials = { id: 8775 };
      email.emailcredentials = emailcredentials;

      const emailcredentialsCollection: IEmailcredentials[] = [{ id: 23400 }];
      jest.spyOn(emailcredentialsService, 'query').mockReturnValue(of(new HttpResponse({ body: emailcredentialsCollection })));
      const additionalEmailcredentials = [emailEmailcredentials, emailcredentials];
      const expectedCollection: IEmailcredentials[] = [...additionalEmailcredentials, ...emailcredentialsCollection];
      jest.spyOn(emailcredentialsService, 'addEmailcredentialsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ email });
      comp.ngOnInit();

      expect(emailcredentialsService.query).toHaveBeenCalled();
      expect(emailcredentialsService.addEmailcredentialsToCollectionIfMissing).toHaveBeenCalledWith(
        emailcredentialsCollection,
        ...additionalEmailcredentials.map(expect.objectContaining),
      );
      expect(comp.emailcredentialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Candidate query and add missing value', () => {
      const email: IEmail = { id: 456 };
      const emailCandidate: ICandidate = { id: 6592 };
      email.emailCandidate = emailCandidate;
      const candidate: ICandidate = { id: 1208 };
      email.candidate = candidate;

      const candidateCollection: ICandidate[] = [{ id: 19890 }];
      jest.spyOn(candidateService, 'query').mockReturnValue(of(new HttpResponse({ body: candidateCollection })));
      const additionalCandidates = [emailCandidate, candidate];
      const expectedCollection: ICandidate[] = [...additionalCandidates, ...candidateCollection];
      jest.spyOn(candidateService, 'addCandidateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ email });
      comp.ngOnInit();

      expect(candidateService.query).toHaveBeenCalled();
      expect(candidateService.addCandidateToCollectionIfMissing).toHaveBeenCalledWith(
        candidateCollection,
        ...additionalCandidates.map(expect.objectContaining),
      );
      expect(comp.candidatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Company query and add missing value', () => {
      const email: IEmail = { id: 456 };
      const emailCompany: ICompany = { id: 5356 };
      email.emailCompany = emailCompany;
      const company: ICompany = { id: 19672 };
      email.company = company;

      const companyCollection: ICompany[] = [{ id: 32636 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [emailCompany, company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ email });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining),
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const email: IEmail = { id: 456 };
      const emailEmailcredentials: IEmailcredentials = { id: 17621 };
      email.emailEmailcredentials = emailEmailcredentials;
      const emailcredentials: IEmailcredentials = { id: 963 };
      email.emailcredentials = emailcredentials;
      const emailCandidate: ICandidate = { id: 12885 };
      email.emailCandidate = emailCandidate;
      const candidate: ICandidate = { id: 25074 };
      email.candidate = candidate;
      const emailCompany: ICompany = { id: 6311 };
      email.emailCompany = emailCompany;
      const company: ICompany = { id: 19527 };
      email.company = company;

      activatedRoute.data = of({ email });
      comp.ngOnInit();

      expect(comp.emailcredentialsSharedCollection).toContain(emailEmailcredentials);
      expect(comp.emailcredentialsSharedCollection).toContain(emailcredentials);
      expect(comp.candidatesSharedCollection).toContain(emailCandidate);
      expect(comp.candidatesSharedCollection).toContain(candidate);
      expect(comp.companiesSharedCollection).toContain(emailCompany);
      expect(comp.companiesSharedCollection).toContain(company);
      expect(comp.email).toEqual(email);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmail>>();
      const email = { id: 123 };
      jest.spyOn(emailFormService, 'getEmail').mockReturnValue(email);
      jest.spyOn(emailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ email });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: email }));
      saveSubject.complete();

      // THEN
      expect(emailFormService.getEmail).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(emailService.update).toHaveBeenCalledWith(expect.objectContaining(email));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmail>>();
      const email = { id: 123 };
      jest.spyOn(emailFormService, 'getEmail').mockReturnValue({ id: null });
      jest.spyOn(emailService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ email: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: email }));
      saveSubject.complete();

      // THEN
      expect(emailFormService.getEmail).toHaveBeenCalled();
      expect(emailService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmail>>();
      const email = { id: 123 };
      jest.spyOn(emailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ email });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emailService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmailcredentials', () => {
      it('Should forward to emailcredentialsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(emailcredentialsService, 'compareEmailcredentials');
        comp.compareEmailcredentials(entity, entity2);
        expect(emailcredentialsService.compareEmailcredentials).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCandidate', () => {
      it('Should forward to candidateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(candidateService, 'compareCandidate');
        comp.compareCandidate(entity, entity2);
        expect(candidateService.compareCandidate).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCompany', () => {
      it('Should forward to companyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyService, 'compareCompany');
        comp.compareCompany(entity, entity2);
        expect(companyService.compareCompany).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
