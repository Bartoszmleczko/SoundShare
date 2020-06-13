import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8886/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  upload(img):  Observable<HttpEvent<any>>{
    const data = new FormData();
    data.append('img', img);
    const req = new HttpRequest('POST', API_URL+'users/img', data, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.httpClient.request(req);
  }

  edit(user) {
    return this.httpClient.put(API_URL + 'users', user);
  }

}
