import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { TokenService } from '../services/token.service';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);
  const token = tokenService.get();

  const authReq = token ? req.clone({ headers: req.headers.set('Authorization', `Bearer ${token}`) }) : req;

  return next(authReq).pipe(catchError((error: HttpErrorResponse) => {
    // Token expiré → déconnecter et rediriger
    if (error.status === 401) {
      tokenService.remove();
      router.navigate(['/login']);
    } else if (error.status === 404) {
        const serverMessage = error.error?.message || "Ressource non trouvée";
          router.navigate(['**']);
    }
    return throwError(() => error);
  }));
};
