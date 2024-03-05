import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IExperience } from 'app/entities/experience/experience.model';
import { ExperienceService } from 'app/entities/experience/service/experience.service';
import { ToolService } from '../service/tool.service';
import { ITool } from '../tool.model';
import { ToolFormService } from './tool-form.service';

import { ToolUpdateComponent } from './tool-update.component';

describe('Tool Management Update Component', () => {
  let comp: ToolUpdateComponent;
  let fixture: ComponentFixture<ToolUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let toolFormService: ToolFormService;
  let toolService: ToolService;
  let experienceService: ExperienceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ToolUpdateComponent],
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
      .overrideTemplate(ToolUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ToolUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    toolFormService = TestBed.inject(ToolFormService);
    toolService = TestBed.inject(ToolService);
    experienceService = TestBed.inject(ExperienceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Experience query and add missing value', () => {
      const tool: ITool = { id: 456 };
      const toolExperience: IExperience = { id: 28829 };
      tool.toolExperience = toolExperience;
      const experience: IExperience = { id: 21126 };
      tool.experience = experience;

      const experienceCollection: IExperience[] = [{ id: 9383 }];
      jest.spyOn(experienceService, 'query').mockReturnValue(of(new HttpResponse({ body: experienceCollection })));
      const additionalExperiences = [toolExperience, experience];
      const expectedCollection: IExperience[] = [...additionalExperiences, ...experienceCollection];
      jest.spyOn(experienceService, 'addExperienceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tool });
      comp.ngOnInit();

      expect(experienceService.query).toHaveBeenCalled();
      expect(experienceService.addExperienceToCollectionIfMissing).toHaveBeenCalledWith(
        experienceCollection,
        ...additionalExperiences.map(expect.objectContaining),
      );
      expect(comp.experiencesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tool: ITool = { id: 456 };
      const toolExperience: IExperience = { id: 21282 };
      tool.toolExperience = toolExperience;
      const experience: IExperience = { id: 20025 };
      tool.experience = experience;

      activatedRoute.data = of({ tool });
      comp.ngOnInit();

      expect(comp.experiencesSharedCollection).toContain(toolExperience);
      expect(comp.experiencesSharedCollection).toContain(experience);
      expect(comp.tool).toEqual(tool);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITool>>();
      const tool = { id: 123 };
      jest.spyOn(toolFormService, 'getTool').mockReturnValue(tool);
      jest.spyOn(toolService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tool });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tool }));
      saveSubject.complete();

      // THEN
      expect(toolFormService.getTool).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(toolService.update).toHaveBeenCalledWith(expect.objectContaining(tool));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITool>>();
      const tool = { id: 123 };
      jest.spyOn(toolFormService, 'getTool').mockReturnValue({ id: null });
      jest.spyOn(toolService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tool: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tool }));
      saveSubject.complete();

      // THEN
      expect(toolFormService.getTool).toHaveBeenCalled();
      expect(toolService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITool>>();
      const tool = { id: 123 };
      jest.spyOn(toolService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tool });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(toolService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareExperience', () => {
      it('Should forward to experienceService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(experienceService, 'compareExperience');
        comp.compareExperience(entity, entity2);
        expect(experienceService.compareExperience).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
