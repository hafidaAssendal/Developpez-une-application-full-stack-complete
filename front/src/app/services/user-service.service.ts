import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { environment } from 'src/environments/environment';
import { UpdateProfileRequest } from '../interfaces/update-profile-request.interface';
import { UpdateProfileResponse } from '../interfaces/update-profile-response.interface';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private http: HttpClient) { }

  getUser():Observable<User> {
    return this.http.get<User>(`${environment.apiUrl}/user/me`);
  }

  updateUser(userReq: UpdateProfileRequest):Observable<UpdateProfileResponse>{
    return this.http.patch<UpdateProfileResponse>(`${environment.apiUrl}/user/me`,userReq);
  }

}
