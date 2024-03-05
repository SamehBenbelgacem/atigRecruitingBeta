import dayjs from 'dayjs/esm';
import { INotification } from 'app/entities/notification/notification.model';
import { INote } from 'app/entities/note/note.model';
import { IDesider } from 'app/entities/desider/desider.model';
import { IOffer } from 'app/entities/offer/offer.model';
import { IEmail } from 'app/entities/email/email.model';
import { ICategory } from 'app/entities/category/category.model';
import { ISubCategory } from 'app/entities/sub-category/sub-category.model';
import { IProcess } from 'app/entities/process/process.model';
import { IProcessStep } from 'app/entities/process-step/process-step.model';
import { ITag } from 'app/entities/tag/tag.model';

export interface ICompany {
  id: number;
  name?: string | null;
  speciality?: string | null;
  logo?: string | null;
  logoContentType?: string | null;
  description?: string | null;
  website?: string | null;
  location?: string | null;
  infoEmail?: string | null;
  phone?: string | null;
  firstContactDate?: dayjs.Dayjs | null;
  notifications?: Pick<INotification, 'id'>[] | null;
  notes?: Pick<INote, 'id'>[] | null;
  desiders?: Pick<IDesider, 'id'>[] | null;
  offers?: Pick<IOffer, 'id'>[] | null;
  emails?: Pick<IEmail, 'id'>[] | null;
  companyCategory?: Pick<ICategory, 'id'> | null;
  companySubCategory?: Pick<ISubCategory, 'id'> | null;
  companyProcess?: Pick<IProcess, 'id'> | null;
  companyProcessStep?: Pick<IProcessStep, 'id'> | null;
  tags?: Pick<ITag, 'id'>[] | null;
  category?: Pick<ICategory, 'id'> | null;
  subCategory?: Pick<ISubCategory, 'id'> | null;
  process?: Pick<IProcess, 'id'> | null;
  processStep?: Pick<IProcessStep, 'id'> | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
