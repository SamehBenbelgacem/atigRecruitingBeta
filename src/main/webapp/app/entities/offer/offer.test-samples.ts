import dayjs from 'dayjs/esm';

import { IOffer, NewOffer } from './offer.model';

export const sampleWithRequiredData: IOffer = {
  id: 29213,
};

export const sampleWithPartialData: IOffer = {
  id: 2106,
  description: 'incidentally whoever',
};

export const sampleWithFullData: IOffer = {
  id: 6303,
  title: 'bite-sized like',
  description: 'plus gadzooks',
  date: dayjs('2024-03-05'),
};

export const sampleWithNewData: NewOffer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
