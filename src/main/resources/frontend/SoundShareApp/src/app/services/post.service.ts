import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';
const API_URL = 'http://localhost:8886/';
@Injectable({
  providedIn: 'root'
})
export class PostService {

  song;

  constructor(private httpClient: HttpClient, private tokenStorage: TokenStorageService) { }

  public getSong(){
    return this.song;
  }
  
  public setSong( song ) {
    this.song = song;
  }

  public addPost(post){
      return this.httpClient.post(API_URL + 'posts', post);
  }
  public getUsersPosts(){
    const user = this.tokenStorage.getUser();
    return this.httpClient.get(API_URL + 'users/' + user.username + '/posts');
  }
  public getPost(id){
    return this.httpClient.get(API_URL + 'posts/' + id);
  }
  public getComments(id){
    return this.httpClient.get(API_URL + 'posts/' + id + '/comments');
  }
  public addComment(comment){
    return this.httpClient.post(API_URL + 'comments', comment);
  }

}
