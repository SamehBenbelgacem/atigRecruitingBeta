import dayjs from 'dayjs/esm';

import { IExperience, NewExperience } from './experience.model';

export const sampleWithRequiredData: IExperience = {
  id: 1107,
};

export const sampleWithPartialData: IExperience = {
  id: 31823,
  companySite: 'whoa apud',
  startDate: dayjs('2024-03-04'),
  duration: 31392.19,
  tasks: 'interestingly patiently down',
};

export const sampleWithFullData: IExperience = {
  id: 16223,
  company: 'mantle reacquaint',
  companySite: 'instead who enthusiastically',
  role: 'cable that gadzooks',
  startDate: dayjs('2024-03-05'),
  endDate: dayjs('2024-03-05'),
  duration: 16506.49,
  location: 'whose brr minus',
  tasks: 'fresh once',
};

export const sampleWithNewData: NewExperience = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
