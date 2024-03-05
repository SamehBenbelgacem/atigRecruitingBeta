import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../object-containing-file.test-samples';

import { ObjectContainingFileFormService } from './object-containing-file-form.service';

describe('ObjectContainingFile Form Service', () => {
  let service: ObjectContainingFileFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObjectContainingFileFormService);
  });

  describe('Service methods', () => {
    describe('createObjectContainingFileFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createObjectContainingFileFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            file: expect.any(Object),
            candidateDocs: expect.any(Object),
            noteDocs: expect.any(Object),
            emailDocs: expect.any(Object),
            candidateAdditionalInfos: expect.any(Object),
            note: expect.any(Object),
            email: expect.any(Object),
          }),
        );
      });

      it('passing IObjectContainingFile should create a new form with FormGroup', () => {
        const formGroup = service.createObjectContainingFileFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            file: expect.any(Object),
            candidateDocs: expect.any(Object),
            noteDocs: expect.any(Object),
            emailDocs: expect.any(Object),
            candidateAdditionalInfos: expect.any(Object),
            note: expect.any(Object),
            email: expect.any(Object),
          }),
        );
      });
    });

    describe('getObjectContainingFile', () => {
      it('should return NewObjectContainingFile for default ObjectContainingFile initial value', () => {
        const formGroup = service.createObjectContainingFileFormGroup(sampleWithNewData);

        const objectContainingFile = service.getObjectContainingFile(formGroup) as any;

        expect(objectContainingFile).toMatchObject(sampleWithNewData);
      });

      it('should return NewObjectContainingFile for empty ObjectContainingFile initial value', () => {
        const formGroup = service.createObjectContainingFileFormGroup();

        const objectContainingFile = service.getObjectContainingFile(formGroup) as any;

        expect(objectContainingFile).toMatchObject({});
      });

      it('should return IObjectContainingFile', () => {
        const formGroup = service.createObjectContainingFileFormGroup(sampleWithRequiredData);

        const objectContainingFile = service.getObjectContainingFile(formGroup) as any;

        expect(objectContainingFile).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IObjectContainingFile should not enable id FormControl', () => {
        const formGroup = service.createObjectContainingFileFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewObjectContainingFile should disable id FormControl', () => {
        const formGroup = service.createObjectContainingFileFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
