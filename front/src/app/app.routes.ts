// src/app/app.routes.ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/home/home.component')
      .then(m => m.HomeComponent)
  },
   {
    path: 'login',
    loadComponent: () => import('./pages/auth/components/login/login.component')
      .then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./pages/auth/components/register/register.component')
      .then(m => m.RegisterComponent)
  },

  {
    path: '**',
    loadComponent: () => import('./shared//not-found/not-found.component')
      .then(m => m.NotFoundComponent)
  }
];