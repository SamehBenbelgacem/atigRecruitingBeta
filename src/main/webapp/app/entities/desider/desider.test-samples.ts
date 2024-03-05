import { IDesider, NewDesider } from './desider.model';

export const sampleWithRequiredData: IDesider = {
  id: 29961,
};

export const sampleWithPartialData: IDesider = {
  id: 611,
  fullName: 'psst',
  email: 'Sheila_Schuster@yahoo.com',
  mobile: 'faithfully bleakly',
};

export const sampleWithFullData: IDesider = {
  id: 27073,
  fullName: 'however coaxingly ready',
  email: 'Hadley.Turcotte@gmail.com',
  mobile: 'warmhearted',
  role: 'gosh',
};

export const sampleWithNewData: NewDesider = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
