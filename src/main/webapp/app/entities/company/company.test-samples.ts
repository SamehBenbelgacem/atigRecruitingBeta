import dayjs from 'dayjs/esm';

import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: 30015,
};

export const sampleWithPartialData: ICompany = {
  id: 12411,
  name: 'swiftly forenenst ew',
  speciality: 'boastfully',
  infoEmail: 'per',
  firstContactDate: dayjs('2024-03-04'),
};

export const sampleWithFullData: ICompany = {
  id: 26987,
  name: 'brilliant',
  speciality: 'aside',
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
  description: 'whose wherever',
  website: 'cautiously yahoo',
  location: 'apud',
  infoEmail: 'why dreamily custom',
  phone: '(661) 996-2762 x810',
  firstContactDate: dayjs('2024-03-04'),
};

export const sampleWithNewData: NewCompany = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
