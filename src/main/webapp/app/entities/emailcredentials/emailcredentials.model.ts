import { IEmail } from 'app/entities/email/email.model';

export interface IEmailcredentials {
  id: number;
  username?: string | null;
  password?: string | null;
  emails?: Pick<IEmail, 'id'>[] | null;
}

export type NewEmailcredentials = Omit<IEmailcredentials, 'id'> & { id: null };
