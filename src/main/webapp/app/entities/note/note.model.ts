import dayjs from 'dayjs/esm';
import { IObjectContainingFile } from 'app/entities/object-containing-file/object-containing-file.model';
import { ICompany } from 'app/entities/company/company.model';
import { ICandidate } from 'app/entities/candidate/candidate.model';

export interface INote {
  id: number;
  title?: string | null;
  date?: dayjs.Dayjs | null;
  description?: string | null;
  documents?: Pick<IObjectContainingFile, 'id'>[] | null;
  noteCompany?: Pick<ICompany, 'id'> | null;
  noteCandidate?: Pick<ICandidate, 'id'> | null;
  candidate?: Pick<ICandidate, 'id'> | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewNote = Omit<INote, 'id'> & { id: null };
