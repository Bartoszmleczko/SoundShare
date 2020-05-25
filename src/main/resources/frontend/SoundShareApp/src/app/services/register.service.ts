import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/entities/user/user.model';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  apiUrl = 'http://localhost:8886/users';
  err: string;
  constructor(private httpClient: HttpClient) { }

  register(newUser: User) {
    const headersOptions = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });
    console.log(newUser);
    
    this.httpClient.post<any>(this.apiUrl, newUser, {headers: headersOptions}).subscribe({
        error: error => {this.err = error;
        console.log(this.err);
        }
      });
    }

}
