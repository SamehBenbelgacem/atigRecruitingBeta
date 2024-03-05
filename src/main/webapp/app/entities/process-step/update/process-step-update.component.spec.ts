import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IProcess } from 'app/entities/process/process.model';
import { ProcessService } from 'app/entities/process/service/process.service';
import { ProcessStepService } from '../service/process-step.service';
import { IProcessStep } from '../process-step.model';
import { ProcessStepFormService } from './process-step-form.service';

import { ProcessStepUpdateComponent } from './process-step-update.component';

describe('ProcessStep Management Update Component', () => {
  let comp: ProcessStepUpdateComponent;
  let fixture: ComponentFixture<ProcessStepUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let processStepFormService: ProcessStepFormService;
  let processStepService: ProcessStepService;
  let processService: ProcessService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProcessStepUpdateComponent],
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
      .overrideTemplate(ProcessStepUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProcessStepUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    processStepFormService = TestBed.inject(ProcessStepFormService);
    processStepService = TestBed.inject(ProcessStepService);
    processService = TestBed.inject(ProcessService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Process query and add missing value', () => {
      const processStep: IProcessStep = { id: 456 };
      const processStepProcess: IProcess = { id: 21669 };
      processStep.processStepProcess = processStepProcess;
      const process: IProcess = { id: 28672 };
      processStep.process = process;

      const processCollection: IProcess[] = [{ id: 7596 }];
      jest.spyOn(processService, 'query').mockReturnValue(of(new HttpResponse({ body: processCollection })));
      const additionalProcesses = [processStepProcess, process];
      const expectedCollection: IProcess[] = [...additionalProcesses, ...processCollection];
      jest.spyOn(processService, 'addProcessToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ processStep });
      comp.ngOnInit();

      expect(processService.query).toHaveBeenCalled();
      expect(processService.addProcessToCollectionIfMissing).toHaveBeenCalledWith(
        processCollection,
        ...additionalProcesses.map(expect.objectContaining),
      );
      expect(comp.processesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const processStep: IProcessStep = { id: 456 };
      const processStepProcess: IProcess = { id: 22269 };
      processStep.processStepProcess = processStepProcess;
      const process: IProcess = { id: 5488 };
      processStep.process = process;

      activatedRoute.data = of({ processStep });
      comp.ngOnInit();

      expect(comp.processesSharedCollection).toContain(processStepProcess);
      expect(comp.processesSharedCollection).toContain(process);
      expect(comp.processStep).toEqual(processStep);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcessStep>>();
      const processStep = { id: 123 };
      jest.spyOn(processStepFormService, 'getProcessStep').mockReturnValue(processStep);
      jest.spyOn(processStepService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ processStep });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: processStep }));
      saveSubject.complete();

      // THEN
      expect(processStepFormService.getProcessStep).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(processStepService.update).toHaveBeenCalledWith(expect.objectContaining(processStep));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcessStep>>();
      const processStep = { id: 123 };
      jest.spyOn(processStepFormService, 'getProcessStep').mockReturnValue({ id: null });
      jest.spyOn(processStepService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ processStep: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: processStep }));
      saveSubject.complete();

      // THEN
      expect(processStepFormService.getProcessStep).toHaveBeenCalled();
      expect(processStepService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcessStep>>();
      const processStep = { id: 123 };
      jest.spyOn(processStepService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ processStep });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(processStepService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProcess', () => {
      it('Should forward to processService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(processService, 'compareProcess');
        comp.compareProcess(entity, entity2);
        expect(processService.compareProcess).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
