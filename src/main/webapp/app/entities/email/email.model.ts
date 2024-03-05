import dayjs from 'dayjs/esm';
import { IObjectContainingFile } from 'app/entities/object-containing-file/object-containing-file.model';
import { ISubEmail } from 'app/entities/sub-email/sub-email.model';
import { IEmailcredentials } from 'app/entities/emailcredentials/emailcredentials.model';
import { ICandidate } from 'app/entities/candidate/candidate.model';
import { ICompany } from 'app/entities/company/company.model';
import { EnumEmailType } from 'app/entities/enumerations/enum-email-type.model';

export interface IEmail {
  id: number;
  from?: string | null;
  recipients?: string | null;
  subject?: string | null;
  text?: string | null;
  type?: keyof typeof EnumEmailType | null;
  date?: dayjs.Dayjs | null;
  snoozedTo?: dayjs.Dayjs | null;
  folder?: string | null;
  signatureText?: string | null;
  signatureImage?: string | null;
  signatureImageContentType?: string | null;
  joinedFiles?: Pick<IObjectContainingFile, 'id'>[] | null;
  subEmails?: Pick<ISubEmail, 'id'>[] | null;
  emailEmailcredentials?: Pick<IEmailcredentials, 'id'> | null;
  emailCandidate?: Pick<ICandidate, 'id'> | null;
  emailCompany?: Pick<ICompany, 'id'> | null;
  candidate?: Pick<ICandidate, 'id'> | null;
  company?: Pick<ICompany, 'id'> | null;
  emailcredentials?: Pick<IEmailcredentials, 'id'> | null;
}

export type NewEmail = Omit<IEmail, 'id'> & { id: null };
