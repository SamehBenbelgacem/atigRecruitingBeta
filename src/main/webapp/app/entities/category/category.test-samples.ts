import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 13996,
};

export const sampleWithPartialData: ICategory = {
  id: 15610,
};

export const sampleWithFullData: ICategory = {
  id: 4268,
  title: 'whenever gray',
};

export const sampleWithNewData: NewCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
