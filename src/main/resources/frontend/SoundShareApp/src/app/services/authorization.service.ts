import { LoginResponse } from './../models/entities/user/loginresponse.model';
import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8886/login';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  constructor(private httpClient: HttpClient ) { }

  login(credentials): Observable<any> {
    return this.httpClient.post<LoginResponse>(AUTH_API, credentials, httpOptions);
  }

}
