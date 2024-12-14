import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '7998c5c0-353e-47cc-a943-a92580a8907e',
};

export const sampleWithPartialData: IAuthority = {
  name: '68adaf41-8217-40b9-a7c7-bfe33f6488c5',
};

export const sampleWithFullData: IAuthority = {
  name: '4acab401-d776-4f4d-b140-14c2524b4a79',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
