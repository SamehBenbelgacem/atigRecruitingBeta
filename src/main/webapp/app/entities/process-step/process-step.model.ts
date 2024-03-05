import { ICandidate } from 'app/entities/candidate/candidate.model';
import { ICompany } from 'app/entities/company/company.model';
import { IProcess } from 'app/entities/process/process.model';
import { EnumPriority } from 'app/entities/enumerations/enum-priority.model';

export interface IProcessStep {
  id: number;
  title?: string | null;
  order?: string | null;
  priority?: keyof typeof EnumPriority | null;
  candidates?: Pick<ICandidate, 'id'>[] | null;
  companies?: Pick<ICompany, 'id'>[] | null;
  processStepProcess?: Pick<IProcess, 'id'> | null;
  process?: Pick<IProcess, 'id'> | null;
}

export type NewProcessStep = Omit<IProcessStep, 'id'> & { id: null };
