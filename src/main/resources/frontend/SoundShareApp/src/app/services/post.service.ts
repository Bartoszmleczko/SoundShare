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

  public getPosts(username){
    return this.httpClient.get(API_URL + 'users/' + username + '/posts');
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
  public editPost(post){
    return this.httpClient.put(API_URL + 'posts', post);
  }
  public deletePost(post_id){
    return this.httpClient.delete(API_URL + 'posts/' + post_id);
  }
  public getFriendsPosts(username){
    return this.httpClient.get(API_URL+'users/' + username + '/friends/posts');
  }
  public addLike(post_id){
    const user = this.tokenStorage.getUser().username;
    console.log(user);
    const like = {postId: post_id, like: user};
    return this.httpClient.put(API_URL + 'posts/likes', like);
  }
  public deleteLike(post_id){
    const user = this.tokenStorage.getUser().username;
    const dislike = {postId: post_id, like: user};
    return this.httpClient.put(API_URL + 'posts/dislikes', dislike);
  }



}
