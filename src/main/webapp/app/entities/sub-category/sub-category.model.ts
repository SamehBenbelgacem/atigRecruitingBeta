import { ICandidate } from 'app/entities/candidate/candidate.model';
import { ICompany } from 'app/entities/company/company.model';
import { ICategory } from 'app/entities/category/category.model';

export interface ISubCategory {
  id: number;
  title?: string | null;
  candidates?: Pick<ICandidate, 'id'>[] | null;
  companies?: Pick<ICompany, 'id'>[] | null;
  subCategoryCategory?: Pick<ICategory, 'id'> | null;
  category?: Pick<ICategory, 'id'> | null;
}

export type NewSubCategory = Omit<ISubCategory, 'id'> & { id: null };
