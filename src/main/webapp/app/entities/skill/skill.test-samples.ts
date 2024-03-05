import { ISkill, NewSkill } from './skill.model';

export const sampleWithRequiredData: ISkill = {
  id: 22266,
};

export const sampleWithPartialData: ISkill = {
  id: 10567,
  title: 'beside',
};

export const sampleWithFullData: ISkill = {
  id: 30822,
  title: 'huzzah',
};

export const sampleWithNewData: NewSkill = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
