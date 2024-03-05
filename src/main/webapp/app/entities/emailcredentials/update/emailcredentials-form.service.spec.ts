import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../emailcredentials.test-samples';

import { EmailcredentialsFormService } from './emailcredentials-form.service';

describe('Emailcredentials Form Service', () => {
  let service: EmailcredentialsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmailcredentialsFormService);
  });

  describe('Service methods', () => {
    describe('createEmailcredentialsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmailcredentialsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            username: expect.any(Object),
            password: expect.any(Object),
          }),
        );
      });

      it('passing IEmailcredentials should create a new form with FormGroup', () => {
        const formGroup = service.createEmailcredentialsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            username: expect.any(Object),
            password: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmailcredentials', () => {
      it('should return NewEmailcredentials for default Emailcredentials initial value', () => {
        const formGroup = service.createEmailcredentialsFormGroup(sampleWithNewData);

        const emailcredentials = service.getEmailcredentials(formGroup) as any;

        expect(emailcredentials).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmailcredentials for empty Emailcredentials initial value', () => {
        const formGroup = service.createEmailcredentialsFormGroup();

        const emailcredentials = service.getEmailcredentials(formGroup) as any;

        expect(emailcredentials).toMatchObject({});
      });

      it('should return IEmailcredentials', () => {
        const formGroup = service.createEmailcredentialsFormGroup(sampleWithRequiredData);

        const emailcredentials = service.getEmailcredentials(formGroup) as any;

        expect(emailcredentials).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmailcredentials should not enable id FormControl', () => {
        const formGroup = service.createEmailcredentialsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmailcredentials should disable id FormControl', () => {
        const formGroup = service.createEmailcredentialsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
