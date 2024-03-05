import dayjs from 'dayjs/esm';

import { INote, NewNote } from './note.model';

export const sampleWithRequiredData: INote = {
  id: 31474,
};

export const sampleWithPartialData: INote = {
  id: 12648,
  title: 'gen',
};

export const sampleWithFullData: INote = {
  id: 26899,
  title: 'unacceptable surprisingly',
  date: dayjs('2024-03-05T06:47'),
  description: 'smoulder',
};

export const sampleWithNewData: NewNote = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
