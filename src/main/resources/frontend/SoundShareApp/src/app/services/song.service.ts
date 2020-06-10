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

  getSongsFromUser(username){
    return this.httpClient.get<any>(API_URL + 'users/' + username + '/songs');
  }

  getSongById(id){
    return this.httpClient.get(API_URL + 'songs/' + id);
  }

  upload(file: File, img: File, title,lyrics): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('title', title);
    formData.append('lyrics', lyrics);
    formData.append('img', img);
    const req = new HttpRequest('POST', API_URL+'songs', formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.httpClient.request(req);
  }

  deleteSong(id: number){
      return this.httpClient.delete(API_URL+'songs/' + id);
  }

  update(song){
    return this.httpClient.put(API_URL + 'songs', song);
  }

  public addLike(song_id){
    const user = this.tokenStorage.getUser().username;
    console.log(user);
    const like = {postId: song_id, like: user};
    return this.httpClient.put(API_URL + 'songs/likes', like);
  }
  public deleteLike(song_id){
    const user = this.tokenStorage.getUser().username;
    const dislike = {postId: song_id, like: user};
    return this.httpClient.put(API_URL + 'songs/dislikes', dislike);
  }

}
