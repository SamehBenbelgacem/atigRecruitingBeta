import dayjs from 'dayjs/esm';
import { ICandidate } from 'app/entities/candidate/candidate.model';

export interface IEducation {
  id: number;
  diploma?: string | null;
  establishment?: string | null;
  mention?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  duration?: number | null;
  location?: string | null;
  educationCandidate?: Pick<ICandidate, 'id'> | null;
  candidate?: Pick<ICandidate, 'id'> | null;
}

export type NewEducation = Omit<IEducation, 'id'> & { id: null };
