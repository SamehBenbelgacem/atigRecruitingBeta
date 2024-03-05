import { IProcessStep, NewProcessStep } from './process-step.model';

export const sampleWithRequiredData: IProcessStep = {
  id: 12112,
};

export const sampleWithPartialData: IProcessStep = {
  id: 20114,
};

export const sampleWithFullData: IProcessStep = {
  id: 15786,
  title: 'palatable',
  order: 'rarely medical',
  priority: 'HIGH',
};

export const sampleWithNewData: NewProcessStep = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
