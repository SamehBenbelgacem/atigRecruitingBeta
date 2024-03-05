import { ISubCategory } from 'app/entities/sub-category/sub-category.model';
import { ICandidate } from 'app/entities/candidate/candidate.model';
import { ICompany } from 'app/entities/company/company.model';

export interface ICategory {
  id: number;
  title?: string | null;
  subCategories?: Pick<ISubCategory, 'id'>[] | null;
  candidates?: Pick<ICandidate, 'id'>[] | null;
  companies?: Pick<ICompany, 'id'>[] | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
