import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../candidate-additional-infos.test-samples';

import { CandidateAdditionalInfosFormService } from './candidate-additional-infos-form.service';

describe('CandidateAdditionalInfos Form Service', () => {
  let service: CandidateAdditionalInfosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CandidateAdditionalInfosFormService);
  });

  describe('Service methods', () => {
    describe('createCandidateAdditionalInfosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCandidateAdditionalInfosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            birthday: expect.any(Object),
            actualSalary: expect.any(Object),
            expectedSalary: expect.any(Object),
            firstContact: expect.any(Object),
            location: expect.any(Object),
            mobile: expect.any(Object),
            disponibility: expect.any(Object),
          }),
        );
      });

      it('passing ICandidateAdditionalInfos should create a new form with FormGroup', () => {
        const formGroup = service.createCandidateAdditionalInfosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            birthday: expect.any(Object),
            actualSalary: expect.any(Object),
            expectedSalary: expect.any(Object),
            firstContact: expect.any(Object),
            location: expect.any(Object),
            mobile: expect.any(Object),
            disponibility: expect.any(Object),
          }),
        );
      });
    });

    describe('getCandidateAdditionalInfos', () => {
      it('should return NewCandidateAdditionalInfos for default CandidateAdditionalInfos initial value', () => {
        const formGroup = service.createCandidateAdditionalInfosFormGroup(sampleWithNewData);

        const candidateAdditionalInfos = service.getCandidateAdditionalInfos(formGroup) as any;

        expect(candidateAdditionalInfos).toMatchObject(sampleWithNewData);
      });

      it('should return NewCandidateAdditionalInfos for empty CandidateAdditionalInfos initial value', () => {
        const formGroup = service.createCandidateAdditionalInfosFormGroup();

        const candidateAdditionalInfos = service.getCandidateAdditionalInfos(formGroup) as any;

        expect(candidateAdditionalInfos).toMatchObject({});
      });

      it('should return ICandidateAdditionalInfos', () => {
        const formGroup = service.createCandidateAdditionalInfosFormGroup(sampleWithRequiredData);

        const candidateAdditionalInfos = service.getCandidateAdditionalInfos(formGroup) as any;

        expect(candidateAdditionalInfos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICandidateAdditionalInfos should not enable id FormControl', () => {
        const formGroup = service.createCandidateAdditionalInfosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCandidateAdditionalInfos should disable id FormControl', () => {
        const formGroup = service.createCandidateAdditionalInfosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
