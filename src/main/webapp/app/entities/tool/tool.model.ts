import { IExperience } from 'app/entities/experience/experience.model';

export interface ITool {
  id: number;
  tool?: string | null;
  toolExperience?: Pick<IExperience, 'id'> | null;
  experience?: Pick<IExperience, 'id'> | null;
}

export type NewTool = Omit<ITool, 'id'> & { id: null };
