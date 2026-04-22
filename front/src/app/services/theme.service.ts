import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Theme } from '../interfaces/theme.interface';
import { MessageResponse } from '../interfaces/messageResponse.interface';
import { AuthService } from '../pages/auth/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  constructor(private http: HttpClient) { }

  getThemes(): Observable<Theme[]> {

    return this.http.get<Theme[]>(`${environment.apiUrl}/themes`);
  }

  subscribe(idTheme: number): Observable<MessageResponse> {

    return this.http.post<MessageResponse>(`${environment.apiUrl}/subscriptions/${idTheme}`, {})
  }
  unSubscribe(idTheme: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/subscriptions/${idTheme}`);
 }
  
  }
