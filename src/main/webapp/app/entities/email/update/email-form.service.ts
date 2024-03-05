import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmail, NewEmail } from '../email.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmail for edit and NewEmailFormGroupInput for create.
 */
type EmailFormGroupInput = IEmail | PartialWithRequiredKeyOf<NewEmail>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmail | NewEmail> = Omit<T, 'date' | 'snoozedTo'> & {
  date?: string | null;
  snoozedTo?: string | null;
};

type EmailFormRawValue = FormValueOf<IEmail>;

type NewEmailFormRawValue = FormValueOf<NewEmail>;

type EmailFormDefaults = Pick<NewEmail, 'id' | 'date' | 'snoozedTo'>;

type EmailFormGroupContent = {
  id: FormControl<EmailFormRawValue['id'] | NewEmail['id']>;
  from: FormControl<EmailFormRawValue['from']>;
  recipients: FormControl<EmailFormRawValue['recipients']>;
  subject: FormControl<EmailFormRawValue['subject']>;
  text: FormControl<EmailFormRawValue['text']>;
  type: FormControl<EmailFormRawValue['type']>;
  date: FormControl<EmailFormRawValue['date']>;
  snoozedTo: FormControl<EmailFormRawValue['snoozedTo']>;
  folder: FormControl<EmailFormRawValue['folder']>;
  signatureText: FormControl<EmailFormRawValue['signatureText']>;
  signatureImage: FormControl<EmailFormRawValue['signatureImage']>;
  signatureImageContentType: FormControl<EmailFormRawValue['signatureImageContentType']>;
  emailEmailcredentials: FormControl<EmailFormRawValue['emailEmailcredentials']>;
  emailCandidate: FormControl<EmailFormRawValue['emailCandidate']>;
  emailCompany: FormControl<EmailFormRawValue['emailCompany']>;
  candidate: FormControl<EmailFormRawValue['candidate']>;
  company: FormControl<EmailFormRawValue['company']>;
  emailcredentials: FormControl<EmailFormRawValue['emailcredentials']>;
};

export type EmailFormGroup = FormGroup<EmailFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmailFormService {
  createEmailFormGroup(email: EmailFormGroupInput = { id: null }): EmailFormGroup {
    const emailRawValue = this.convertEmailToEmailRawValue({
      ...this.getFormDefaults(),
      ...email,
    });
    return new FormGroup<EmailFormGroupContent>({
      id: new FormControl(
        { value: emailRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      from: new FormControl(emailRawValue.from),
      recipients: new FormControl(emailRawValue.recipients),
      subject: new FormControl(emailRawValue.subject),
      text: new FormControl(emailRawValue.text),
      type: new FormControl(emailRawValue.type),
      date: new FormControl(emailRawValue.date),
      snoozedTo: new FormControl(emailRawValue.snoozedTo),
      folder: new FormControl(emailRawValue.folder),
      signatureText: new FormControl(emailRawValue.signatureText),
      signatureImage: new FormControl(emailRawValue.signatureImage),
      signatureImageContentType: new FormControl(emailRawValue.signatureImageContentType),
      emailEmailcredentials: new FormControl(emailRawValue.emailEmailcredentials),
      emailCandidate: new FormControl(emailRawValue.emailCandidate),
      emailCompany: new FormControl(emailRawValue.emailCompany),
      candidate: new FormControl(emailRawValue.candidate),
      company: new FormControl(emailRawValue.company),
      emailcredentials: new FormControl(emailRawValue.emailcredentials),
    });
  }

  getEmail(form: EmailFormGroup): IEmail | NewEmail {
    return this.convertEmailRawValueToEmail(form.getRawValue() as EmailFormRawValue | NewEmailFormRawValue);
  }

  resetForm(form: EmailFormGroup, email: EmailFormGroupInput): void {
    const emailRawValue = this.convertEmailToEmailRawValue({ ...this.getFormDefaults(), ...email });
    form.reset(
      {
        ...emailRawValue,
        id: { value: emailRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmailFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
      snoozedTo: currentTime,
    };
  }

  private convertEmailRawValueToEmail(rawEmail: EmailFormRawValue | NewEmailFormRawValue): IEmail | NewEmail {
    return {
      ...rawEmail,
      date: dayjs(rawEmail.date, DATE_TIME_FORMAT),
      snoozedTo: dayjs(rawEmail.snoozedTo, DATE_TIME_FORMAT),
    };
  }

  private convertEmailToEmailRawValue(
    email: IEmail | (Partial<NewEmail> & EmailFormDefaults),
  ): EmailFormRawValue | PartialWithRequiredKeyOf<NewEmailFormRawValue> {
    return {
      ...email,
      date: email.date ? email.date.format(DATE_TIME_FORMAT) : undefined,
      snoozedTo: email.snoozedTo ? email.snoozedTo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
