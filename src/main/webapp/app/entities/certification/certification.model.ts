import dayjs from 'dayjs/esm';
import { ICandidate } from 'app/entities/candidate/candidate.model';

export interface ICertification {
  id: number;
  title?: string | null;
  date?: dayjs.Dayjs | null;
  certificationCandidate?: Pick<ICandidate, 'id'> | null;
  candidate?: Pick<ICandidate, 'id'> | null;
}

export type NewCertification = Omit<ICertification, 'id'> & { id: null };
