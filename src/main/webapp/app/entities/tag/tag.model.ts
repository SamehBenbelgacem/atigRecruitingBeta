import { ICompany } from 'app/entities/company/company.model';
import { IOffer } from 'app/entities/offer/offer.model';
import { ICandidate } from 'app/entities/candidate/candidate.model';

export interface ITag {
  id: number;
  title?: string | null;
  companies?: Pick<ICompany, 'id'>[] | null;
  offers?: Pick<IOffer, 'id'>[] | null;
  candidates?: Pick<ICandidate, 'id'>[] | null;
}

export type NewTag = Omit<ITag, 'id'> & { id: null };
