import { ICandidate } from 'app/entities/candidate/candidate.model';

export interface ISkill {
  id: number;
  title?: string | null;
  skillCandidate?: Pick<ICandidate, 'id'> | null;
  candidate?: Pick<ICandidate, 'id'> | null;
}

export type NewSkill = Omit<ISkill, 'id'> & { id: null };
