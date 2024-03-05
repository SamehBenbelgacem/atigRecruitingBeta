import dayjs from 'dayjs/esm';

import { IEducation, NewEducation } from './education.model';

export const sampleWithRequiredData: IEducation = {
  id: 13680,
};

export const sampleWithPartialData: IEducation = {
  id: 19900,
  mention: 'blah vaguely',
  startDate: dayjs('2024-03-04'),
};

export const sampleWithFullData: IEducation = {
  id: 20726,
  diploma: 'unless underneath',
  establishment: 'illiteracy vastly',
  mention: 'at',
  startDate: dayjs('2024-03-04'),
  endDate: dayjs('2024-03-04'),
  duration: 22853.43,
  location: 'mercerise',
};

export const sampleWithNewData: NewEducation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
