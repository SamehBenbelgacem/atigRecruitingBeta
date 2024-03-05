import dayjs from 'dayjs/esm';

import { ICandidateAdditionalInfos, NewCandidateAdditionalInfos } from './candidate-additional-infos.model';

export const sampleWithRequiredData: ICandidateAdditionalInfos = {
  id: 389,
};

export const sampleWithPartialData: ICandidateAdditionalInfos = {
  id: 25403,
  firstContact: dayjs('2024-03-04T08:09'),
  location: 'lazily',
  disponibility: 'mouse because',
};

export const sampleWithFullData: ICandidateAdditionalInfos = {
  id: 13720,
  birthday: dayjs('2024-03-04T14:41'),
  actualSalary: 5900,
  expectedSalary: 22841,
  firstContact: dayjs('2024-03-04T07:33'),
  location: 'daintily provided',
  mobile: 'geez whenever badly',
  disponibility: 'throughout excluding',
};

export const sampleWithNewData: NewCandidateAdditionalInfos = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
