import { Injectable } from '@angular/core';

/**
 * To avoid the NG0200 error (circular dependency), we created this independent service.
 * It allows us to manage tokens without relying on other dependencies.
 */

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'jwt_token';
  constructor() { }

  get(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }
  set(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }
  remove(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
  hasToken(): boolean {
     return this.get()!==null;
  }
}
