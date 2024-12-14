import { IDepartamento } from 'app/entities/departamento/departamento.model';

export interface IEmpleado {
  id: number;
  nombreEmpleado?: string | null;
  apellidoEmpleado?: string | null;
  telefono?: string | null;
  correo?: string | null;
  departamento?: IDepartamento | null;
}

export type NewEmpleado = Omit<IEmpleado, 'id'> & { id: null };
