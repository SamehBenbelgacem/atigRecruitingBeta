import dayjs from 'dayjs/esm';

import { IEvent, NewEvent } from './event.model';

export const sampleWithRequiredData: IEvent = {
  id: 1492,
};

export const sampleWithPartialData: IEvent = {
  id: 2301,
  externalParticipants: 'sans quarrelsomely ack',
  description: 'drill subcontract weepy',
};

export const sampleWithFullData: IEvent = {
  id: 3891,
  title: 'wheeze',
  externalParticipants: 'scholarly in finally',
  date: dayjs('2024-03-05T07:00'),
  duration: '1581',
  description: 'coolly upward',
  priority: 'MEDUIM',
};

export const sampleWithNewData: NewEvent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
