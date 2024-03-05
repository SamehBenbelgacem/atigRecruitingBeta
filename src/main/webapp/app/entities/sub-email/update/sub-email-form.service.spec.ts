import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sub-email.test-samples';

import { SubEmailFormService } from './sub-email-form.service';

describe('SubEmail Form Service', () => {
  let service: SubEmailFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubEmailFormService);
  });

  describe('Service methods', () => {
    describe('createSubEmailFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSubEmailFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            from: expect.any(Object),
            recipients: expect.any(Object),
            text: expect.any(Object),
            type: expect.any(Object),
            date: expect.any(Object),
            snoozedTo: expect.any(Object),
            signatureText: expect.any(Object),
            signatureImage: expect.any(Object),
            subEmailEmail: expect.any(Object),
            email: expect.any(Object),
          }),
        );
      });

      it('passing ISubEmail should create a new form with FormGroup', () => {
        const formGroup = service.createSubEmailFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            from: expect.any(Object),
            recipients: expect.any(Object),
            text: expect.any(Object),
            type: expect.any(Object),
            date: expect.any(Object),
            snoozedTo: expect.any(Object),
            signatureText: expect.any(Object),
            signatureImage: expect.any(Object),
            subEmailEmail: expect.any(Object),
            email: expect.any(Object),
          }),
        );
      });
    });

    describe('getSubEmail', () => {
      it('should return NewSubEmail for default SubEmail initial value', () => {
        const formGroup = service.createSubEmailFormGroup(sampleWithNewData);

        const subEmail = service.getSubEmail(formGroup) as any;

        expect(subEmail).toMatchObject(sampleWithNewData);
      });

      it('should return NewSubEmail for empty SubEmail initial value', () => {
        const formGroup = service.createSubEmailFormGroup();

        const subEmail = service.getSubEmail(formGroup) as any;

        expect(subEmail).toMatchObject({});
      });

      it('should return ISubEmail', () => {
        const formGroup = service.createSubEmailFormGroup(sampleWithRequiredData);

        const subEmail = service.getSubEmail(formGroup) as any;

        expect(subEmail).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISubEmail should not enable id FormControl', () => {
        const formGroup = service.createSubEmailFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSubEmail should disable id FormControl', () => {
        const formGroup = service.createSubEmailFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
