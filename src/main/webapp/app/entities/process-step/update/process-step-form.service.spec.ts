// import { TestBed } from '@angular/core/testing';
//
// import { sampleWithRequiredData, sampleWithNewData } from '../process-step.test-samples';
//
// import { ProcessStepFormService } from './process-step-form.service';
//
// describe('ProcessStep Form Service', () => {
//   let service: ProcessStepFormService;
//
//   beforeEach(() => {
//     TestBed.configureTestingModule({});
//     service = TestBed.inject(ProcessStepFormService);
//   });
//
//   describe('Service methods', () => {
//     describe('createProcessStepFormGroup', () => {
//       it('should create a new form with FormControl', () => {
//         const formGroup = service.createProcessStepFormGroup();
//
//         expect(formGroup.controls).toEqual(
//           expect.objectContaining({
//             id: expect.any(Object),
//             title: expect.any(Object),
//             order: expect.any(Object),
//             priority: expect.any(Object),
//             processStepProcess: expect.any(Object),
//             process: expect.any(Object),
//           }),
//         );
//       });
//
//       it('passing IProcessStep should create a new form with FormGroup', () => {
//         const formGroup = service.createProcessStepFormGroup(sampleWithRequiredData);
//
//         expect(formGroup.controls).toEqual(
//           expect.objectContaining({
//             id: expect.any(Object),
//             title: expect.any(Object),
//             order: expect.any(Object),
//             priority: expect.any(Object),
//             processStepProcess: expect.any(Object),
//             process: expect.any(Object),
//           }),
//         );
//       });
//     });
//
//     describe('getProcessStep', () => {
//       it('should return NewProcessStep for default ProcessStep initial value', () => {
//         const formGroup = service.createProcessStepFormGroup(sampleWithNewData);
//
//         const processStep = service.getProcessStep(formGroup) as any;
//
//         expect(processStep).toMatchObject(sampleWithNewData);
//       });
//
//       it('should return NewProcessStep for empty ProcessStep initial value', () => {
//         const formGroup = service.createProcessStepFormGroup();
//
//         const processStep = service.getProcessStep(formGroup) as any;
//
//         expect(processStep).toMatchObject({});
//       });
//
//       it('should return IProcessStep', () => {
//         const formGroup = service.createProcessStepFormGroup(sampleWithRequiredData);
//
//         const processStep = service.getProcessStep(formGroup) as any;
//
//         expect(processStep).toMatchObject(sampleWithRequiredData);
//       });
//     });
//
//     describe('resetForm', () => {
//       it('passing IProcessStep should not enable id FormControl', () => {
//         const formGroup = service.createProcessStepFormGroup();
//         expect(formGroup.controls.id.disabled).toBe(true);
//
//         service.resetForm(formGroup, sampleWithRequiredData);
//
//         expect(formGroup.controls.id.disabled).toBe(true);
//       });
//
//       it('passing NewProcessStep should disable id FormControl', () => {
//         const formGroup = service.createProcessStepFormGroup(sampleWithRequiredData);
//         expect(formGroup.controls.id.disabled).toBe(true);
//
//         service.resetForm(formGroup, { id: null });
//
//         expect(formGroup.controls.id.disabled).toBe(true);
//       });
//     });
//   });
// });
