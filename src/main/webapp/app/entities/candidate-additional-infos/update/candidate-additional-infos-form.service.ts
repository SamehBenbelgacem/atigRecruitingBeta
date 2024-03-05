// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import dayjs from 'dayjs/esm';
// import { DATE_TIME_FORMAT } from 'app/config/input.constants';
// import { ICandidateAdditionalInfos, NewCandidateAdditionalInfos } from '../candidate-additional-infos.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts ICandidateAdditionalInfos for edit and NewCandidateAdditionalInfosFormGroupInput for create.
//  */
// type CandidateAdditionalInfosFormGroupInput = ICandidateAdditionalInfos | PartialWithRequiredKeyOf<NewCandidateAdditionalInfos>;
//
// /**
//  * Type that converts some properties for forms.
//  */
// type FormValueOf<T extends ICandidateAdditionalInfos | NewCandidateAdditionalInfos> = Omit<T, 'birthday' | 'firstContact'> & {
//   birthday?: string | null;
//   firstContact?: string | null;
// };
//
// type CandidateAdditionalInfosFormRawValue = FormValueOf<ICandidateAdditionalInfos>;
//
// type NewCandidateAdditionalInfosFormRawValue = FormValueOf<NewCandidateAdditionalInfos>;
//
// type CandidateAdditionalInfosFormDefaults = Pick<NewCandidateAdditionalInfos, 'id' | 'birthday' | 'firstContact'>;
//
// type CandidateAdditionalInfosFormGroupContent = {
//   id: FormControl<CandidateAdditionalInfosFormRawValue['id'] | NewCandidateAdditionalInfos['id']>;
//   birthday: FormControl<CandidateAdditionalInfosFormRawValue['birthday']>;
//   actualSalary: FormControl<CandidateAdditionalInfosFormRawValue['actualSalary']>;
//   expectedSalary: FormControl<CandidateAdditionalInfosFormRawValue['expectedSalary']>;
//   firstContact: FormControl<CandidateAdditionalInfosFormRawValue['firstContact']>;
//   location: FormControl<CandidateAdditionalInfosFormRawValue['location']>;
//   mobile: FormControl<CandidateAdditionalInfosFormRawValue['mobile']>;
//   disponibility: FormControl<CandidateAdditionalInfosFormRawValue['disponibility']>;
// };
//
// export type CandidateAdditionalInfosFormGroup = FormGroup<CandidateAdditionalInfosFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class CandidateAdditionalInfosFormService {
//   createCandidateAdditionalInfosFormGroup(
//     candidateAdditionalInfos: CandidateAdditionalInfosFormGroupInput = { id: null },
//   ): CandidateAdditionalInfosFormGroup {
//     const candidateAdditionalInfosRawValue = this.convertCandidateAdditionalInfosToCandidateAdditionalInfosRawValue({
//       ...this.getFormDefaults(),
//       ...candidateAdditionalInfos,
//     });
//     return new FormGroup<CandidateAdditionalInfosFormGroupContent>({
//       id: new FormControl(
//         { value: candidateAdditionalInfosRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       birthday: new FormControl(candidateAdditionalInfosRawValue.birthday),
//       actualSalary: new FormControl(candidateAdditionalInfosRawValue.actualSalary),
//       expectedSalary: new FormControl(candidateAdditionalInfosRawValue.expectedSalary),
//       firstContact: new FormControl(candidateAdditionalInfosRawValue.firstContact),
//       location: new FormControl(candidateAdditionalInfosRawValue.location),
//       mobile: new FormControl(candidateAdditionalInfosRawValue.mobile),
//       disponibility: new FormControl(candidateAdditionalInfosRawValue.disponibility),
//     });
//   }
//
//   getCandidateAdditionalInfos(form: CandidateAdditionalInfosFormGroup): ICandidateAdditionalInfos | NewCandidateAdditionalInfos {
//     return this.convertCandidateAdditionalInfosRawValueToCandidateAdditionalInfos(
//       form.getRawValue() as CandidateAdditionalInfosFormRawValue | NewCandidateAdditionalInfosFormRawValue,
//     );
//   }
//
//   resetForm(form: CandidateAdditionalInfosFormGroup, candidateAdditionalInfos: CandidateAdditionalInfosFormGroupInput): void {
//     const candidateAdditionalInfosRawValue = this.convertCandidateAdditionalInfosToCandidateAdditionalInfosRawValue({
//       ...this.getFormDefaults(),
//       ...candidateAdditionalInfos,
//     });
//     form.reset(
//       {
//         ...candidateAdditionalInfosRawValue,
//         id: { value: candidateAdditionalInfosRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): CandidateAdditionalInfosFormDefaults {
//     const currentTime = dayjs();
//
//     return {
//       id: null,
//       birthday: currentTime,
//       firstContact: currentTime,
//     };
//   }
//
//   private convertCandidateAdditionalInfosRawValueToCandidateAdditionalInfos(
//     rawCandidateAdditionalInfos: CandidateAdditionalInfosFormRawValue | NewCandidateAdditionalInfosFormRawValue,
//   ): ICandidateAdditionalInfos | NewCandidateAdditionalInfos {
//     return {
//       ...rawCandidateAdditionalInfos,
//       birthday: dayjs(rawCandidateAdditionalInfos.birthday, DATE_TIME_FORMAT),
//       firstContact: dayjs(rawCandidateAdditionalInfos.firstContact, DATE_TIME_FORMAT),
//     };
//   }
//
//   private convertCandidateAdditionalInfosToCandidateAdditionalInfosRawValue(
//     candidateAdditionalInfos: ICandidateAdditionalInfos | (Partial<NewCandidateAdditionalInfos> & CandidateAdditionalInfosFormDefaults),
//   ): CandidateAdditionalInfosFormRawValue | PartialWithRequiredKeyOf<NewCandidateAdditionalInfosFormRawValue> {
//     return {
//       ...candidateAdditionalInfos,
//       birthday: candidateAdditionalInfos.birthday ? candidateAdditionalInfos.birthday.format(DATE_TIME_FORMAT) : undefined,
//       firstContact: candidateAdditionalInfos.firstContact ? candidateAdditionalInfos.firstContact.format(DATE_TIME_FORMAT) : undefined,
//     };
//   }
// }
