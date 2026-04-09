import { computed, inject, Injectable, signal } from '@angular/core';
import { LoginRequest } from '../interfaces/login-request.inerface';
import { HttpClient } from '@angular/common/http';
import { JwtResponse } from '../interfaces/jwt-response.interface';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SignupRequest } from '../interfaces/signup-request.interface';
import { User } from 'src/app/interfaces/user.interface';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Signals
  private currentUser = signal<User | null>(null);
  isAuthenticated = computed(() => this.currentUser() !== null);
  user = computed(() => this.currentUser());

  constructor(private httpClient: HttpClient,
    private tokenService: TokenService,
    private route: Router) {

    if (this.tokenService.hasToken()) {
      this.loadCurrentUser().subscribe();
    }

  }

  //Login
  public login(request: LoginRequest): Observable<JwtResponse> {

    return this.httpClient.post<JwtResponse>(`${environment.apiUrl}/auth/login`, request)
      .pipe(
        tap(response => {
          this.tokenService.set(response.token);
          this.loadCurrentUser().subscribe();

        })
      );

  }

  // load current user
  private loadCurrentUser(): Observable<User> {
    return this.httpClient.get<User>(`${environment.apiUrl}/user/me`).pipe(
      tap(user => {
        //  console.log('[AuthService] User chargé :', user);
        this.currentUser.set(user);

      }));
  }

  //register
  public register(request: SignupRequest): Observable<string> {
    return this.httpClient.post<string>(`${environment.apiUrl}/auth/register`, request);

  }

  //logout
  public logout(): void {
    this.tokenService.remove();
    this.currentUser.set(null);
    this.route.navigate([`/`]);
  }

}
