import { IObjectContainingFile, NewObjectContainingFile } from './object-containing-file.model';

export const sampleWithRequiredData: IObjectContainingFile = {
  id: 20791,
};

export const sampleWithPartialData: IObjectContainingFile = {
  id: 26201,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithFullData: IObjectContainingFile = {
  id: 23484,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithNewData: NewObjectContainingFile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
