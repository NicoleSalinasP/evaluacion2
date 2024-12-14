import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'evaluacion2App.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'empleado',
    data: { pageTitle: 'evaluacion2App.empleado.home.title' },
    loadChildren: () => import('./empleado/empleado.routes'),
  },
  {
    path: 'departamento',
    data: { pageTitle: 'evaluacion2App.departamento.home.title' },
    loadChildren: () => import('./departamento/departamento.routes'),
  },
  {
    path: 'jefe',
    data: { pageTitle: 'evaluacion2App.jefe.home.title' },
    loadChildren: () => import('./jefe/jefe.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
