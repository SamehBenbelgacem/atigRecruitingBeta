import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { DesiderService } from '../service/desider.service';
import { IDesider } from '../desider.model';
import { DesiderFormService } from './desider-form.service';

import { DesiderUpdateComponent } from './desider-update.component';

describe('Desider Management Update Component', () => {
  let comp: DesiderUpdateComponent;
  let fixture: ComponentFixture<DesiderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let desiderFormService: DesiderFormService;
  let desiderService: DesiderService;
  let companyService: CompanyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DesiderUpdateComponent],
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
      .overrideTemplate(DesiderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DesiderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    desiderFormService = TestBed.inject(DesiderFormService);
    desiderService = TestBed.inject(DesiderService);
    companyService = TestBed.inject(CompanyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Company query and add missing value', () => {
      const desider: IDesider = { id: 456 };
      const desiderCompany: ICompany = { id: 15222 };
      desider.desiderCompany = desiderCompany;
      const company: ICompany = { id: 4269 };
      desider.company = company;

      const companyCollection: ICompany[] = [{ id: 22765 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [desiderCompany, company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ desider });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining),
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const desider: IDesider = { id: 456 };
      const desiderCompany: ICompany = { id: 9838 };
      desider.desiderCompany = desiderCompany;
      const company: ICompany = { id: 5848 };
      desider.company = company;

      activatedRoute.data = of({ desider });
      comp.ngOnInit();

      expect(comp.companiesSharedCollection).toContain(desiderCompany);
      expect(comp.companiesSharedCollection).toContain(company);
      expect(comp.desider).toEqual(desider);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDesider>>();
      const desider = { id: 123 };
      jest.spyOn(desiderFormService, 'getDesider').mockReturnValue(desider);
      jest.spyOn(desiderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ desider });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: desider }));
      saveSubject.complete();

      // THEN
      expect(desiderFormService.getDesider).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(desiderService.update).toHaveBeenCalledWith(expect.objectContaining(desider));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDesider>>();
      const desider = { id: 123 };
      jest.spyOn(desiderFormService, 'getDesider').mockReturnValue({ id: null });
      jest.spyOn(desiderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ desider: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: desider }));
      saveSubject.complete();

      // THEN
      expect(desiderFormService.getDesider).toHaveBeenCalled();
      expect(desiderService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDesider>>();
      const desider = { id: 123 };
      jest.spyOn(desiderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ desider });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(desiderService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
