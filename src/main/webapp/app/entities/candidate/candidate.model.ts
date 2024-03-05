import { ICandidateAdditionalInfos } from 'app/entities/candidate-additional-infos/candidate-additional-infos.model';
import { IExperience } from 'app/entities/experience/experience.model';
import { IEducation } from 'app/entities/education/education.model';
import { ICertification } from 'app/entities/certification/certification.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { ILanguage } from 'app/entities/language/language.model';
import { INotification } from 'app/entities/notification/notification.model';
import { INote } from 'app/entities/note/note.model';
import { IEmail } from 'app/entities/email/email.model';
import { ICategory } from 'app/entities/category/category.model';
import { ISubCategory } from 'app/entities/sub-category/sub-category.model';
import { IProcess } from 'app/entities/process/process.model';
import { IProcessStep } from 'app/entities/process-step/process-step.model';
import { ITag } from 'app/entities/tag/tag.model';

export interface ICandidate {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  profession?: string | null;
  nbExperience?: number | null;
  personalEmail?: string | null;
  additionalInfos?: Pick<ICandidateAdditionalInfos, 'id'> | null;
  experiences?: Pick<IExperience, 'id'>[] | null;
  educations?: Pick<IEducation, 'id'>[] | null;
  certifications?: Pick<ICertification, 'id'>[] | null;
  skills?: Pick<ISkill, 'id'>[] | null;
  languages?: Pick<ILanguage, 'id'>[] | null;
  notifications?: Pick<INotification, 'id'>[] | null;
  notes?: Pick<INote, 'id'>[] | null;
  emails?: Pick<IEmail, 'id'>[] | null;
  candidateCategory?: Pick<ICategory, 'id'> | null;
  candidateSubCategory?: Pick<ISubCategory, 'id'> | null;
  candidateProcess?: Pick<IProcess, 'id'> | null;
  candidateProcessStep?: Pick<IProcessStep, 'id'> | null;
  tags?: Pick<ITag, 'id'>[] | null;
  category?: Pick<ICategory, 'id'> | null;
  subCategory?: Pick<ISubCategory, 'id'> | null;
  process?: Pick<IProcess, 'id'> | null;
  processStep?: Pick<IProcessStep, 'id'> | null;
}

export type NewCandidate = Omit<ICandidate, 'id'> & { id: null };
