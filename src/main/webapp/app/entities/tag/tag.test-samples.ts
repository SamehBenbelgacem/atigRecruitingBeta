import { ITag, NewTag } from './tag.model';

export const sampleWithRequiredData: ITag = {
  id: 31441,
};

export const sampleWithPartialData: ITag = {
  id: 24698,
  title: 'geez',
};

export const sampleWithFullData: ITag = {
  id: 1876,
  title: 'embody jab profitable',
};

export const sampleWithNewData: NewTag = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
