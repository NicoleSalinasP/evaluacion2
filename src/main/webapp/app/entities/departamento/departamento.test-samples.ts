import { IDepartamento, NewDepartamento } from './departamento.model';

export const sampleWithRequiredData: IDepartamento = {
  id: 20690,
};

export const sampleWithPartialData: IDepartamento = {
  id: 9990,
  nombreDepartamento: 'upwardly',
  presupuestoDepartamento: 519.06,
};

export const sampleWithFullData: IDepartamento = {
  id: 575,
  nombreDepartamento: 'of but',
  ubicacionDepartamento: 'ew cap swill',
  presupuestoDepartamento: 19578.7,
};

export const sampleWithNewData: NewDepartamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
