import { IProcessStep } from 'app/entities/process-step/process-step.model';
import { ICandidate } from 'app/entities/candidate/candidate.model';
import { ICompany } from 'app/entities/company/company.model';
import { EnumProsessType } from 'app/entities/enumerations/enum-prosess-type.model';

export interface IProcess {
  id: number;
  title?: string | null;
  type?: keyof typeof EnumProsessType | null;
  steps?: Pick<IProcessStep, 'id'>[] | null;
  candidates?: Pick<ICandidate, 'id'>[] | null;
  companies?: Pick<ICompany, 'id'>[] | null;
}

export type NewProcess = Omit<IProcess, 'id'> & { id: null };
