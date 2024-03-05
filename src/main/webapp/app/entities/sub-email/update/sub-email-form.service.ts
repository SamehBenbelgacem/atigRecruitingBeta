// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import dayjs from 'dayjs/esm';
// import { DATE_TIME_FORMAT } from 'app/config/input.constants';
// import { ISubEmail, NewSubEmail } from '../sub-email.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts ISubEmail for edit and NewSubEmailFormGroupInput for create.
//  */
// type SubEmailFormGroupInput = ISubEmail | PartialWithRequiredKeyOf<NewSubEmail>;
//
// /**
//  * Type that converts some properties for forms.
//  */
// type FormValueOf<T extends ISubEmail | NewSubEmail> = Omit<T, 'date' | 'snoozedTo'> & {
//   date?: string | null;
//   snoozedTo?: string | null;
// };
//
// type SubEmailFormRawValue = FormValueOf<ISubEmail>;
//
// type NewSubEmailFormRawValue = FormValueOf<NewSubEmail>;
//
// type SubEmailFormDefaults = Pick<NewSubEmail, 'id' | 'date' | 'snoozedTo'>;
//
// type SubEmailFormGroupContent = {
//   id: FormControl<SubEmailFormRawValue['id'] | NewSubEmail['id']>;
//   from: FormControl<SubEmailFormRawValue['from']>;
//   recipients: FormControl<SubEmailFormRawValue['recipients']>;
//   text: FormControl<SubEmailFormRawValue['text']>;
//   type: FormControl<SubEmailFormRawValue['type']>;
//   date: FormControl<SubEmailFormRawValue['date']>;
//   snoozedTo: FormControl<SubEmailFormRawValue['snoozedTo']>;
//   signatureText: FormControl<SubEmailFormRawValue['signatureText']>;
//   signatureImage: FormControl<SubEmailFormRawValue['signatureImage']>;
//   signatureImageContentType: FormControl<SubEmailFormRawValue['signatureImageContentType']>;
//   subEmailEmail: FormControl<SubEmailFormRawValue['subEmailEmail']>;
//   email: FormControl<SubEmailFormRawValue['email']>;
// };
//
// export type SubEmailFormGroup = FormGroup<SubEmailFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class SubEmailFormService {
//   createSubEmailFormGroup(subEmail: SubEmailFormGroupInput = { id: null }): SubEmailFormGroup {
//     const subEmailRawValue = this.convertSubEmailToSubEmailRawValue({
//       ...this.getFormDefaults(),
//       ...subEmail,
//     });
//     return new FormGroup<SubEmailFormGroupContent>({
//       id: new FormControl(
//         { value: subEmailRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       from: new FormControl(subEmailRawValue.from),
//       recipients: new FormControl(subEmailRawValue.recipients),
//       text: new FormControl(subEmailRawValue.text),
//       type: new FormControl(subEmailRawValue.type),
//       date: new FormControl(subEmailRawValue.date),
//       snoozedTo: new FormControl(subEmailRawValue.snoozedTo),
//       signatureText: new FormControl(subEmailRawValue.signatureText),
//       signatureImage: new FormControl(subEmailRawValue.signatureImage),
//       signatureImageContentType: new FormControl(subEmailRawValue.signatureImageContentType),
//       subEmailEmail: new FormControl(subEmailRawValue.subEmailEmail),
//       email: new FormControl(subEmailRawValue.email),
//     });
//   }
//
//   getSubEmail(form: SubEmailFormGroup): ISubEmail | NewSubEmail {
//     return this.convertSubEmailRawValueToSubEmail(form.getRawValue() as SubEmailFormRawValue | NewSubEmailFormRawValue);
//   }
//
//   resetForm(form: SubEmailFormGroup, subEmail: SubEmailFormGroupInput): void {
//     const subEmailRawValue = this.convertSubEmailToSubEmailRawValue({ ...this.getFormDefaults(), ...subEmail });
//     form.reset(
//       {
//         ...subEmailRawValue,
//         id: { value: subEmailRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): SubEmailFormDefaults {
//     const currentTime = dayjs();
//
//     return {
//       id: null,
//       date: currentTime,
//       snoozedTo: currentTime,
//     };
//   }
//
//   private convertSubEmailRawValueToSubEmail(rawSubEmail: SubEmailFormRawValue | NewSubEmailFormRawValue): ISubEmail | NewSubEmail {
//     return {
//       ...rawSubEmail,
//       date: dayjs(rawSubEmail.date, DATE_TIME_FORMAT),
//       snoozedTo: dayjs(rawSubEmail.snoozedTo, DATE_TIME_FORMAT),
//     };
//   }
//
//   private convertSubEmailToSubEmailRawValue(
//     subEmail: ISubEmail | (Partial<NewSubEmail> & SubEmailFormDefaults),
//   ): SubEmailFormRawValue | PartialWithRequiredKeyOf<NewSubEmailFormRawValue> {
//     return {
//       ...subEmail,
//       date: subEmail.date ? subEmail.date.format(DATE_TIME_FORMAT) : undefined,
//       snoozedTo: subEmail.snoozedTo ? subEmail.snoozedTo.format(DATE_TIME_FORMAT) : undefined,
//     };
//   }
// }
