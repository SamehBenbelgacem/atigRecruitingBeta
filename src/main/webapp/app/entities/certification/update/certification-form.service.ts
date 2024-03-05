// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import { ICertification, NewCertification } from '../certification.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts ICertification for edit and NewCertificationFormGroupInput for create.
//  */
// type CertificationFormGroupInput = ICertification | PartialWithRequiredKeyOf<NewCertification>;
//
// type CertificationFormDefaults = Pick<NewCertification, 'id'>;
//
// type CertificationFormGroupContent = {
//   id: FormControl<ICertification['id'] | NewCertification['id']>;
//   title: FormControl<ICertification['title']>;
//   date: FormControl<ICertification['date']>;
//   certificationCandidate: FormControl<ICertification['certificationCandidate']>;
//   candidate: FormControl<ICertification['candidate']>;
// };
//
// export type CertificationFormGroup = FormGroup<CertificationFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class CertificationFormService {
//   createCertificationFormGroup(certification: CertificationFormGroupInput = { id: null }): CertificationFormGroup {
//     const certificationRawValue = {
//       ...this.getFormDefaults(),
//       ...certification,
//     };
//     return new FormGroup<CertificationFormGroupContent>({
//       id: new FormControl(
//         { value: certificationRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       title: new FormControl(certificationRawValue.title),
//       date: new FormControl(certificationRawValue.date),
//       certificationCandidate: new FormControl(certificationRawValue.certificationCandidate),
//       candidate: new FormControl(certificationRawValue.candidate),
//     });
//   }
//
//   getCertification(form: CertificationFormGroup): ICertification | NewCertification {
//     return form.getRawValue() as ICertification | NewCertification;
//   }
//
//   resetForm(form: CertificationFormGroup, certification: CertificationFormGroupInput): void {
//     const certificationRawValue = { ...this.getFormDefaults(), ...certification };
//     form.reset(
//       {
//         ...certificationRawValue,
//         id: { value: certificationRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): CertificationFormDefaults {
//     return {
//       id: null,
//     };
//   }
// }
