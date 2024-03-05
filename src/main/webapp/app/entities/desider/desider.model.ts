import { ICompany } from 'app/entities/company/company.model';

export interface IDesider {
  id: number;
  fullName?: string | null;
  email?: string | null;
  mobile?: string | null;
  role?: string | null;
  desiderCompany?: Pick<ICompany, 'id'> | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewDesider = Omit<IDesider, 'id'> & { id: null };
