// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { guestGuard } from './guards/guest.guard';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    canActivate: [guestGuard],
    loadComponent: () => import('./pages/home/home.component')
      .then(m => m.HomeComponent)
  },
  {
    path: 'login',
    canActivate: [guestGuard],
    loadComponent: () => import('./pages/auth/components/login/login.component')
      .then(m => m.LoginComponent)
  },
  {
    path: 'register',
    canActivate: [guestGuard],
    loadComponent: () => import('./pages/auth/components/register/register.component')
      .then(m => m.RegisterComponent)
  },
  {
    path: 'articles',
    canActivate: [authGuard],
    loadComponent: () => import('./pages/features/articles/list/articles.component')
      .then(m => m.ArticlesComponent)
  },
  {
    path: '**',
    loadComponent: () => import('./shared//not-found/not-found.component')
      .then(m => m.NotFoundComponent)
  }
];