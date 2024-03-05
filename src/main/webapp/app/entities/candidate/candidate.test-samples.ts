import { ICandidate, NewCandidate } from './candidate.model';

export const sampleWithRequiredData: ICandidate = {
  id: 20712,
  firstName: 'Shayna',
  lastName: 'Towne',
  profession: 'emergence busily horseradish',
  personalEmail: 'primary',
};

export const sampleWithPartialData: ICandidate = {
  id: 24923,
  firstName: 'Tyrell',
  lastName: "O'Hara",
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  profession: 'meanwhile for frantically',
  personalEmail: 'gee now',
};

export const sampleWithFullData: ICandidate = {
  id: 25205,
  firstName: 'Furman',
  lastName: 'Reilly',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  profession: 'barring injunction',
  nbExperience: 16817,
  personalEmail: 'suddenly proliferate',
};

export const sampleWithNewData: NewCandidate = {
  firstName: 'Dorian',
  lastName: 'Lang',
  profession: 'giant',
  personalEmail: 'ha even',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
