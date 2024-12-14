import { IJefe, NewJefe } from './jefe.model';

export const sampleWithRequiredData: IJefe = {
  id: 21149,
};

export const sampleWithPartialData: IJefe = {
  id: 19650,
};

export const sampleWithFullData: IJefe = {
  id: 32009,
  nombreJefe: 'swat',
  telefono: 'glisten resolve',
};

export const sampleWithNewData: NewJefe = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
