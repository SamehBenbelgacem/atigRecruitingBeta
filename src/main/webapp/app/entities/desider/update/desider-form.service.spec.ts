// import { TestBed } from '@angular/core/testing';
//
// import { sampleWithRequiredData, sampleWithNewData } from '../desider.test-samples';
//
// import { DesiderFormService } from './desider-form.service';
//
// describe('Desider Form Service', () => {
//   let service: DesiderFormService;
//
//   beforeEach(() => {
//     TestBed.configureTestingModule({});
//     service = TestBed.inject(DesiderFormService);
//   });
//
//   describe('Service methods', () => {
//     describe('createDesiderFormGroup', () => {
//       it('should create a new form with FormControl', () => {
//         const formGroup = service.createDesiderFormGroup();
//
//         expect(formGroup.controls).toEqual(
//           expect.objectContaining({
//             id: expect.any(Object),
//             fullName: expect.any(Object),
//             email: expect.any(Object),
//             mobile: expect.any(Object),
//             role: expect.any(Object),
//             desiderCompany: expect.any(Object),
//             company: expect.any(Object),
//           }),
//         );
//       });
//
//       it('passing IDesider should create a new form with FormGroup', () => {
//         const formGroup = service.createDesiderFormGroup(sampleWithRequiredData);
//
//         expect(formGroup.controls).toEqual(
//           expect.objectContaining({
//             id: expect.any(Object),
//             fullName: expect.any(Object),
//             email: expect.any(Object),
//             mobile: expect.any(Object),
//             role: expect.any(Object),
//             desiderCompany: expect.any(Object),
//             company: expect.any(Object),
//           }),
//         );
//       });
//     });
//
//     describe('getDesider', () => {
//       it('should return NewDesider for default Desider initial value', () => {
//         const formGroup = service.createDesiderFormGroup(sampleWithNewData);
//
//         const desider = service.getDesider(formGroup) as any;
//
//         expect(desider).toMatchObject(sampleWithNewData);
//       });
//
//       it('should return NewDesider for empty Desider initial value', () => {
//         const formGroup = service.createDesiderFormGroup();
//
//         const desider = service.getDesider(formGroup) as any;
//
//         expect(desider).toMatchObject({});
//       });
//
//       it('should return IDesider', () => {
//         const formGroup = service.createDesiderFormGroup(sampleWithRequiredData);
//
//         const desider = service.getDesider(formGroup) as any;
//
//         expect(desider).toMatchObject(sampleWithRequiredData);
//       });
//     });
//
//     describe('resetForm', () => {
//       it('passing IDesider should not enable id FormControl', () => {
//         const formGroup = service.createDesiderFormGroup();
//         expect(formGroup.controls.id.disabled).toBe(true);
//
//         service.resetForm(formGroup, sampleWithRequiredData);
//
//         expect(formGroup.controls.id.disabled).toBe(true);
//       });
//
//       it('passing NewDesider should disable id FormControl', () => {
//         const formGroup = service.createDesiderFormGroup(sampleWithRequiredData);
//         expect(formGroup.controls.id.disabled).toBe(true);
//
//         service.resetForm(formGroup, { id: null });
//
//         expect(formGroup.controls.id.disabled).toBe(true);
//       });
//     });
//   });
// });
