import dayjs from 'dayjs/esm';

import { IEmail, NewEmail } from './email.model';

export const sampleWithRequiredData: IEmail = {
  id: 30906,
};

export const sampleWithPartialData: IEmail = {
  id: 2130,
  subject: 'than anenst track',
  date: dayjs('2024-03-04T22:51'),
  signatureText: 'aside nor',
};

export const sampleWithFullData: IEmail = {
  id: 24614,
  from: 'supposing huzzah failing',
  recipients: 'geez now drat',
  subject: 'account phase',
  text: 'utterly phooey where',
  type: 'INBOX',
  date: dayjs('2024-03-05T05:36'),
  snoozedTo: dayjs('2024-03-05T02:14'),
  folder: 'swipe deceivingly',
  signatureText: 'gadzooks far',
  signatureImage: '../fake-data/blob/hipster.png',
  signatureImageContentType: 'unknown',
};

export const sampleWithNewData: NewEmail = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
