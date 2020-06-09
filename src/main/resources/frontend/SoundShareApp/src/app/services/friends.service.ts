import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenStorageService } from './token-storage.service';
import { User } from '../models/entities/user/user.model';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8886/';

@Injectable({
  providedIn: 'root'
})
export class FriendsService {

  username: string;

  constructor(private httpClient: HttpClient, private tokenStorage: TokenStorageService) { 
  }

  public getUsersFriends() {
    this.username = this.tokenStorage.getUser().username;
      return this.httpClient.get(API_URL + 'users/' + this.username + '/relationships');
  }

  public getClickedUserFriends(username): Observable<User[]>{
    return this.httpClient.get<User[]>(API_URL + 'users/' + username + '/relationships');
  }

  public getUsers(queryName: string) {

      return this.httpClient.get(API_URL + 'users?username=' + queryName);
  }

  public getUser(username){
    return this.httpClient.get(API_URL + 'users/' + username);
  }

  public invite(receivingUserId){
      const invite = {friendId: receivingUserId, isActive: false};
      return this.httpClient.post(API_URL + 'relationships', invite);
  }

  public getInvitesToUser(username){
    return this.httpClient.get<any[]>(API_URL + 'users/' + username + '/friends');
  }

  public getRequestFromUser(username){
    return this.httpClient.get(API_URL + 'users/' + username + '/requests');
  }
  
  public acceptInvitation(inv){
    return this.httpClient.put(API_URL + 'relationships', inv);
  }

}
