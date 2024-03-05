import dayjs from 'dayjs/esm';

import { ICertification, NewCertification } from './certification.model';

export const sampleWithRequiredData: ICertification = {
  id: 18075,
};

export const sampleWithPartialData: ICertification = {
  id: 14583,
  date: dayjs('2024-03-05'),
};

export const sampleWithFullData: ICertification = {
  id: 19741,
  title: 'welfare',
  date: dayjs('2024-03-04'),
};

export const sampleWithNewData: NewCertification = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
