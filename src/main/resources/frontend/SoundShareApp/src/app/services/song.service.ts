import { map } from 'rxjs/operators';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

const API_URL = 'http://localhost:8886/';

@Injectable({
  providedIn: 'root'
})
export class SongService {

  songs;
  song;
  constructor(private httpClient: HttpClient, private tokenStorage: TokenStorageService) { }

  getSongs(){
    const username = this.tokenStorage.getUser().username;
    return this.httpClient.get<any>(API_URL + 'users/' + username + '/songs');
    
  }
  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest('POST', API_URL+'songs', formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.httpClient.request(req);
  }

  deleteSong(id: number){
      return this.httpClient.delete(API_URL+'songs/' + id);
  }

}
