import { IEmpleado, NewEmpleado } from './empleado.model';

export const sampleWithRequiredData: IEmpleado = {
  id: 26278,
};

export const sampleWithPartialData: IEmpleado = {
  id: 32310,
  nombreEmpleado: 'knowledgeably',
  telefono: 'but inventory',
};

export const sampleWithFullData: IEmpleado = {
  id: 11515,
  nombreEmpleado: 'greedy attraction vanadyl',
  apellidoEmpleado: 'but fictionalize minor',
  telefono: 'claw yowza',
  correo: 'because wholly',
};

export const sampleWithNewData: NewEmpleado = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
