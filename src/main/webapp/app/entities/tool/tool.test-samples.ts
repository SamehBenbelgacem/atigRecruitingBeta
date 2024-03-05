import { ITool, NewTool } from './tool.model';

export const sampleWithRequiredData: ITool = {
  id: 15058,
};

export const sampleWithPartialData: ITool = {
  id: 13962,
};

export const sampleWithFullData: ITool = {
  id: 13947,
  tool: 'whenever',
};

export const sampleWithNewData: NewTool = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
