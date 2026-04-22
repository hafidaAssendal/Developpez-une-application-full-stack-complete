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
    path: 'themes',
    canActivate: [authGuard],
    loadComponent: () => import('./pages/features/themes/theme/theme.component')
      .then(m => m.ThemeComponent)
  },
  {
    path:'articles/create',
    canActivate:[authGuard],
    loadComponent:()=>import(`./pages/features/articles/create-article/create-article.component`)
    .then(m=>m.CreateArticleComponent)

  },
   {
    path: 'articles/:id',
    canActivate: [authGuard],
    loadComponent: () => import('./pages/features/articles/detail-article/detail-article.component')
      .then(m => m.DetailArticleComponent)
  },
  {
    path:'profile',
    canActivate:[authGuard],
    loadComponent:()=>import('./shared/profile/profile.component')
    .then(m=>m.ProfileComponent)
  },
  {
    path: '**',
    loadComponent: () => import('./shared//not-found/not-found.component')
      .then(m => m.NotFoundComponent)
  }
];