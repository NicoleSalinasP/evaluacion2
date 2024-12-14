export interface IJefe {
  id: number;
  nombreJefe?: string | null;
  telefono?: string | null;
}

export type NewJefe = Omit<IJefe, 'id'> & { id: null };
