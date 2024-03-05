// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import { IEmailcredentials, NewEmailcredentials } from '../emailcredentials.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts IEmailcredentials for edit and NewEmailcredentialsFormGroupInput for create.
//  */
// type EmailcredentialsFormGroupInput = IEmailcredentials | PartialWithRequiredKeyOf<NewEmailcredentials>;
//
// type EmailcredentialsFormDefaults = Pick<NewEmailcredentials, 'id'>;
//
// type EmailcredentialsFormGroupContent = {
//   id: FormControl<IEmailcredentials['id'] | NewEmailcredentials['id']>;
//   username: FormControl<IEmailcredentials['username']>;
//   password: FormControl<IEmailcredentials['password']>;
// };
//
// export type EmailcredentialsFormGroup = FormGroup<EmailcredentialsFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class EmailcredentialsFormService {
//   createEmailcredentialsFormGroup(emailcredentials: EmailcredentialsFormGroupInput = { id: null }): EmailcredentialsFormGroup {
//     const emailcredentialsRawValue = {
//       ...this.getFormDefaults(),
//       ...emailcredentials,
//     };
//     return new FormGroup<EmailcredentialsFormGroupContent>({
//       id: new FormControl(
//         { value: emailcredentialsRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       username: new FormControl(emailcredentialsRawValue.username),
//       password: new FormControl(emailcredentialsRawValue.password),
//     });
//   }
//
//   getEmailcredentials(form: EmailcredentialsFormGroup): IEmailcredentials | NewEmailcredentials {
//     return form.getRawValue() as IEmailcredentials | NewEmailcredentials;
//   }
//
//   resetForm(form: EmailcredentialsFormGroup, emailcredentials: EmailcredentialsFormGroupInput): void {
//     const emailcredentialsRawValue = { ...this.getFormDefaults(), ...emailcredentials };
//     form.reset(
//       {
//         ...emailcredentialsRawValue,
//         id: { value: emailcredentialsRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): EmailcredentialsFormDefaults {
//     return {
//       id: null,
//     };
//   }
// }
