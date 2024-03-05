import dayjs from 'dayjs/esm';
import { IEmail } from 'app/entities/email/email.model';
import { EnumEmailType } from 'app/entities/enumerations/enum-email-type.model';

export interface ISubEmail {
  id: number;
  from?: string | null;
  recipients?: string | null;
  text?: string | null;
  type?: keyof typeof EnumEmailType | null;
  date?: dayjs.Dayjs | null;
  snoozedTo?: dayjs.Dayjs | null;
  signatureText?: string | null;
  signatureImage?: string | null;
  signatureImageContentType?: string | null;
  subEmailEmail?: Pick<IEmail, 'id'> | null;
  email?: Pick<IEmail, 'id'> | null;
}

export type NewSubEmail = Omit<ISubEmail, 'id'> & { id: null };
