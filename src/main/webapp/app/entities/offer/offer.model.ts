import dayjs from 'dayjs/esm';
import { ICompany } from 'app/entities/company/company.model';
import { ITag } from 'app/entities/tag/tag.model';

export interface IOffer {
  id: number;
  title?: string | null;
  description?: string | null;
  date?: dayjs.Dayjs | null;
  offerCompany?: Pick<ICompany, 'id'> | null;
  tags?: Pick<ITag, 'id'>[] | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewOffer = Omit<IOffer, 'id'> & { id: null };
