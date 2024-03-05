import dayjs from 'dayjs/esm';
import { ITool } from 'app/entities/tool/tool.model';
import { ICandidate } from 'app/entities/candidate/candidate.model';

export interface IExperience {
  id: number;
  company?: string | null;
  companySite?: string | null;
  role?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  duration?: number | null;
  location?: string | null;
  tasks?: string | null;
  tools?: Pick<ITool, 'id'>[] | null;
  experienceCandidate?: Pick<ICandidate, 'id'> | null;
  candidate?: Pick<ICandidate, 'id'> | null;
}

export type NewExperience = Omit<IExperience, 'id'> & { id: null };
