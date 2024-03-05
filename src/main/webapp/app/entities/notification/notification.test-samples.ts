import dayjs from 'dayjs/esm';

import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: 1783,
  attention: 'MEDUIM',
};

export const sampleWithPartialData: INotification = {
  id: 8739,
  attention: 'HIGH',
  type: 'FORCANDIDATE',
};

export const sampleWithFullData: INotification = {
  id: 16370,
  message: 'kindly ugh oh',
  callUpDate: dayjs('2024-03-04T22:07'),
  readStatus: false,
  attention: 'HIGH',
  type: 'GENERAl',
};

export const sampleWithNewData: NewNotification = {
  attention: 'MEDUIM',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
