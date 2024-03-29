import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../certification.test-samples';

import { CertificationFormService } from './certification-form.service';

describe('Certification Form Service', () => {
  let service: CertificationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CertificationFormService);
  });

  describe('Service methods', () => {
    describe('createCertificationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCertificationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            date: expect.any(Object),
            certificationCandidate: expect.any(Object),
            candidate: expect.any(Object),
          }),
        );
      });

      it('passing ICertification should create a new form with FormGroup', () => {
        const formGroup = service.createCertificationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            date: expect.any(Object),
            certificationCandidate: expect.any(Object),
            candidate: expect.any(Object),
          }),
        );
      });
    });

    describe('getCertification', () => {
      it('should return NewCertification for default Certification initial value', () => {
        const formGroup = service.createCertificationFormGroup(sampleWithNewData);

        const certification = service.getCertification(formGroup) as any;

        expect(certification).toMatchObject(sampleWithNewData);
      });

      it('should return NewCertification for empty Certification initial value', () => {
        const formGroup = service.createCertificationFormGroup();

        const certification = service.getCertification(formGroup) as any;

        expect(certification).toMatchObject({});
      });

      it('should return ICertification', () => {
        const formGroup = service.createCertificationFormGroup(sampleWithRequiredData);

        const certification = service.getCertification(formGroup) as any;

        expect(certification).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICertification should not enable id FormControl', () => {
        const formGroup = service.createCertificationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCertification should disable id FormControl', () => {
        const formGroup = service.createCertificationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
