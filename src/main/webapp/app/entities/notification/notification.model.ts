import dayjs from 'dayjs/esm';
import { ICandidate } from 'app/entities/candidate/candidate.model';
import { ICompany } from 'app/entities/company/company.model';
import { EnumPriority } from 'app/entities/enumerations/enum-priority.model';
import { EnumNotificationType } from 'app/entities/enumerations/enum-notification-type.model';

export interface INotification {
  id: number;
  message?: string | null;
  callUpDate?: dayjs.Dayjs | null;
  readStatus?: boolean | null;
  attention?: keyof typeof EnumPriority | null;
  type?: keyof typeof EnumNotificationType | null;
  notificationCandidate?: Pick<ICandidate, 'id'> | null;
  notificationCompany?: Pick<ICompany, 'id'> | null;
  candidate?: Pick<ICandidate, 'id'> | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewNotification = Omit<INotification, 'id'> & { id: null };
