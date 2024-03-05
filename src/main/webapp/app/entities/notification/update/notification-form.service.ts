// import { Injectable } from '@angular/core';
// import { FormGroup, FormControl, Validators } from '@angular/forms';
//
// import dayjs from 'dayjs/esm';
// import { DATE_TIME_FORMAT } from 'app/config/input.constants';
// import { INotification, NewNotification } from '../notification.model';
//
// /**
//  * A partial Type with required key is used as form input.
//  */
// type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };
//
// /**
//  * Type for createFormGroup and resetForm argument.
//  * It accepts INotification for edit and NewNotificationFormGroupInput for create.
//  */
// type NotificationFormGroupInput = INotification | PartialWithRequiredKeyOf<NewNotification>;
//
// /**
//  * Type that converts some properties for forms.
//  */
// type FormValueOf<T extends INotification | NewNotification> = Omit<T, 'callUpDate'> & {
//   callUpDate?: string | null;
// };
//
// type NotificationFormRawValue = FormValueOf<INotification>;
//
// type NewNotificationFormRawValue = FormValueOf<NewNotification>;
//
// type NotificationFormDefaults = Pick<NewNotification, 'id' | 'callUpDate' | 'readStatus'>;
//
// type NotificationFormGroupContent = {
//   id: FormControl<NotificationFormRawValue['id'] | NewNotification['id']>;
//   message: FormControl<NotificationFormRawValue['message']>;
//   callUpDate: FormControl<NotificationFormRawValue['callUpDate']>;
//   readStatus: FormControl<NotificationFormRawValue['readStatus']>;
//   attention: FormControl<NotificationFormRawValue['attention']>;
//   type: FormControl<NotificationFormRawValue['type']>;
//   notificationCandidate: FormControl<NotificationFormRawValue['notificationCandidate']>;
//   notificationCompany: FormControl<NotificationFormRawValue['notificationCompany']>;
//   candidate: FormControl<NotificationFormRawValue['candidate']>;
//   company: FormControl<NotificationFormRawValue['company']>;
// };
//
// export type NotificationFormGroup = FormGroup<NotificationFormGroupContent>;
//
// @Injectable({ providedIn: 'root' })
// export class NotificationFormService {
//   createNotificationFormGroup(notification: NotificationFormGroupInput = { id: null }): NotificationFormGroup {
//     const notificationRawValue = this.convertNotificationToNotificationRawValue({
//       ...this.getFormDefaults(),
//       ...notification,
//     });
//     return new FormGroup<NotificationFormGroupContent>({
//       id: new FormControl(
//         { value: notificationRawValue.id, disabled: true },
//         {
//           nonNullable: true,
//           validators: [Validators.required],
//         },
//       ),
//       message: new FormControl(notificationRawValue.message),
//       callUpDate: new FormControl(notificationRawValue.callUpDate),
//       readStatus: new FormControl(notificationRawValue.readStatus),
//       attention: new FormControl(notificationRawValue.attention, {
//         validators: [Validators.required],
//       }),
//       type: new FormControl(notificationRawValue.type),
//       notificationCandidate: new FormControl(notificationRawValue.notificationCandidate),
//       notificationCompany: new FormControl(notificationRawValue.notificationCompany),
//       candidate: new FormControl(notificationRawValue.candidate),
//       company: new FormControl(notificationRawValue.company),
//     });
//   }
//
//   getNotification(form: NotificationFormGroup): INotification | NewNotification {
//     return this.convertNotificationRawValueToNotification(form.getRawValue() as NotificationFormRawValue | NewNotificationFormRawValue);
//   }
//
//   resetForm(form: NotificationFormGroup, notification: NotificationFormGroupInput): void {
//     const notificationRawValue = this.convertNotificationToNotificationRawValue({ ...this.getFormDefaults(), ...notification });
//     form.reset(
//       {
//         ...notificationRawValue,
//         id: { value: notificationRawValue.id, disabled: true },
//       } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
//     );
//   }
//
//   private getFormDefaults(): NotificationFormDefaults {
//     const currentTime = dayjs();
//
//     return {
//       id: null,
//       callUpDate: currentTime,
//       readStatus: false,
//     };
//   }
//
//   private convertNotificationRawValueToNotification(
//     rawNotification: NotificationFormRawValue | NewNotificationFormRawValue,
//   ): INotification | NewNotification {
//     return {
//       ...rawNotification,
//       callUpDate: dayjs(rawNotification.callUpDate, DATE_TIME_FORMAT),
//     };
//   }
//
//   private convertNotificationToNotificationRawValue(
//     notification: INotification | (Partial<NewNotification> & NotificationFormDefaults),
//   ): NotificationFormRawValue | PartialWithRequiredKeyOf<NewNotificationFormRawValue> {
//     return {
//       ...notification,
//       callUpDate: notification.callUpDate ? notification.callUpDate.format(DATE_TIME_FORMAT) : undefined,
//     };
//   }
// }
