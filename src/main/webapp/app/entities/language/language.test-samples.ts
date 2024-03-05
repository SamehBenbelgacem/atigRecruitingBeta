import { ILanguage, NewLanguage } from './language.model';

export const sampleWithRequiredData: ILanguage = {
  id: 22431,
};

export const sampleWithPartialData: ILanguage = {
  id: 8817,
  name: 'GERMAN',
};

export const sampleWithFullData: ILanguage = {
  id: 24967,
  name: 'ENGLISH',
  level: 'C2',
};

export const sampleWithNewData: NewLanguage = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
