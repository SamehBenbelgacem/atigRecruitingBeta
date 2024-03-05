import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { EnumPriority } from 'app/entities/enumerations/enum-priority.model';

export interface IEvent {
  id: number;
  title?: string | null;
  externalParticipants?: string | null;
  date?: dayjs.Dayjs | null;
  duration?: string | null;
  description?: string | null;
  priority?: keyof typeof EnumPriority | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewEvent = Omit<IEvent, 'id'> & { id: null };
