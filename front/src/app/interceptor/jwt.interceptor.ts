import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../pages/auth/services/auth.service';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getToken();

  const authReq = token ? req.clone({ headers: req.headers.set('Authorization', `Bearer ${token}`) }) : req;

  return next(authReq).pipe(catchError((error: HttpErrorResponse) => {
    // Token expiré → déconnecter et rediriger
    if (error.status === 401) {
      authService.logout();
      router.navigate(['/login']);
    }
    return throwError(() => error);
  }));
};
