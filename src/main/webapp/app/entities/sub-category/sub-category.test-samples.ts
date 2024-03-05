import { ISubCategory, NewSubCategory } from './sub-category.model';

export const sampleWithRequiredData: ISubCategory = {
  id: 715,
};

export const sampleWithPartialData: ISubCategory = {
  id: 6757,
  title: 'unethically',
};

export const sampleWithFullData: ISubCategory = {
  id: 16451,
  title: 'since',
};

export const sampleWithNewData: NewSubCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
