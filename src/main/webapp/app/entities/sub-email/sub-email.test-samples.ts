import dayjs from 'dayjs/esm';

import { ISubEmail, NewSubEmail } from './sub-email.model';

export const sampleWithRequiredData: ISubEmail = {
  id: 5489,
};

export const sampleWithPartialData: ISubEmail = {
  id: 27738,
  from: 'brr',
  recipients: 'daddy funny oof',
  signatureImage: '../fake-data/blob/hipster.png',
  signatureImageContentType: 'unknown',
};

export const sampleWithFullData: ISubEmail = {
  id: 22754,
  from: 'demist boo redial',
  recipients: 'gee',
  text: 'absent gripe snob',
  type: 'SNOOZED',
  date: dayjs('2024-03-04T17:05'),
  snoozedTo: dayjs('2024-03-04T16:25'),
  signatureText: 'because swoop fairly',
  signatureImage: '../fake-data/blob/hipster.png',
  signatureImageContentType: 'unknown',
};

export const sampleWithNewData: NewSubEmail = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
