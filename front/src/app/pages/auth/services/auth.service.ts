import { computed, Injectable, signal } from '@angular/core';
import { LoginRequest } from '../interfaces/login-request.inerface';
import { HttpClient } from '@angular/common/http';
import { JwtResponse } from '../interfaces/jwt-response.interface';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SignupRequest } from '../interfaces/signup-request.interface';
import { User } from 'src/app/interfaces/user.interface';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'jwt_token';

  // Signals
  private currentUser = signal<User | null>(null);
  isAuthenticated = computed(() => this.currentUser() !== null);
  user = computed(() => this.currentUser());

  constructor(private httpClient: HttpClient, private route: Router) {
    console.log('[AuthService] Token présent ?', this.getToken());
    if (this.getToken()) {
      this.loadCurrentUser().subscribe();
    }

  }

  //Login
  public login(request: LoginRequest): Observable<JwtResponse> {

    return this.httpClient.post<JwtResponse>(`${environment.apiUrl}/auth/login`, request)
      .pipe(
        tap(response => {
          localStorage.setItem(this.TOKEN_KEY, response.token);
          this.loadCurrentUser().subscribe();

        })
      );

  }

  // load current user
  private loadCurrentUser(): Observable<User> {
    return this.httpClient.get<User>(`${environment.apiUrl}/user/me`).pipe(
      tap(user => {
        console.log('[AuthService] User chargé :', user);
        this.currentUser.set(user);

      }));
  }

  public getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  //register
  public register(request: SignupRequest): Observable<string> {
    return this.httpClient.post<string>(`${environment.apiUrl}/auth/register`, request);

  }

  //logout
  public logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this.currentUser.set(null);
    this.route.navigate([`/`]);
  }

}
