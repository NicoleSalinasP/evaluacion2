import { IJefe } from 'app/entities/jefe/jefe.model';

export interface IDepartamento {
  id: number;
  nombreDepartamento?: string | null;
  ubicacionDepartamento?: string | null;
  presupuestoDepartamento?: number | null;
  jefe?: IJefe | null;
}

export type NewDepartamento = Omit<IDepartamento, 'id'> & { id: null };
