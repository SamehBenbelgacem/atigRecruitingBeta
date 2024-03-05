import dayjs from 'dayjs/esm';
import { IObjectContainingFile } from 'app/entities/object-containing-file/object-containing-file.model';
import { ICandidate } from 'app/entities/candidate/candidate.model';

export interface ICandidateAdditionalInfos {
  id: number;
  birthday?: dayjs.Dayjs | null;
  actualSalary?: number | null;
  expectedSalary?: number | null;
  firstContact?: dayjs.Dayjs | null;
  location?: string | null;
  mobile?: string | null;
  disponibility?: string | null;
  documents?: Pick<IObjectContainingFile, 'id'>[] | null;
  candidate?: Pick<ICandidate, 'id'> | null;
}

export type NewCandidateAdditionalInfos = Omit<ICandidateAdditionalInfos, 'id'> & { id: null };
