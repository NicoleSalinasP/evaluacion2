import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 16314,
  login: 'aKM',
};

export const sampleWithPartialData: IUser = {
  id: 17267,
  login: 'q~=@5jNq',
};

export const sampleWithFullData: IUser = {
  id: 30175,
  login: 'cHy@Ku\\!3a',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
