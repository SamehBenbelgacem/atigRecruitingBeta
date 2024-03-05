import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tool.test-samples';

import { ToolFormService } from './tool-form.service';

describe('Tool Form Service', () => {
  let service: ToolFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ToolFormService);
  });

  describe('Service methods', () => {
    describe('createToolFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createToolFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tool: expect.any(Object),
            toolExperience: expect.any(Object),
            experience: expect.any(Object),
          }),
        );
      });

      it('passing ITool should create a new form with FormGroup', () => {
        const formGroup = service.createToolFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tool: expect.any(Object),
            toolExperience: expect.any(Object),
            experience: expect.any(Object),
          }),
        );
      });
    });

    describe('getTool', () => {
      it('should return NewTool for default Tool initial value', () => {
        const formGroup = service.createToolFormGroup(sampleWithNewData);

        const tool = service.getTool(formGroup) as any;

        expect(tool).toMatchObject(sampleWithNewData);
      });

      it('should return NewTool for empty Tool initial value', () => {
        const formGroup = service.createToolFormGroup();

        const tool = service.getTool(formGroup) as any;

        expect(tool).toMatchObject({});
      });

      it('should return ITool', () => {
        const formGroup = service.createToolFormGroup(sampleWithRequiredData);

        const tool = service.getTool(formGroup) as any;

        expect(tool).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITool should not enable id FormControl', () => {
        const formGroup = service.createToolFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTool should disable id FormControl', () => {
        const formGroup = service.createToolFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
