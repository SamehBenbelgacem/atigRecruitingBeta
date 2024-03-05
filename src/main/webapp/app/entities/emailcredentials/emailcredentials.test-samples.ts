import { IEmailcredentials, NewEmailcredentials } from './emailcredentials.model';

export const sampleWithRequiredData: IEmailcredentials = {
  id: 23685,
};

export const sampleWithPartialData: IEmailcredentials = {
  id: 13447,
  username: 'shopping',
};

export const sampleWithFullData: IEmailcredentials = {
  id: 23912,
  username: 'instead',
  password: 'air inasmuch',
};

export const sampleWithNewData: NewEmailcredentials = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
