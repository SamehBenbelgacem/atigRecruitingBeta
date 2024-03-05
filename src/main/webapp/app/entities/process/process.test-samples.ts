import { IProcess, NewProcess } from './process.model';

export const sampleWithRequiredData: IProcess = {
  id: 29126,
};

export const sampleWithPartialData: IProcess = {
  id: 9420,
};

export const sampleWithFullData: IProcess = {
  id: 3153,
  title: 'clonk unto',
  type: 'FORCOMPANY',
};

export const sampleWithNewData: NewProcess = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
