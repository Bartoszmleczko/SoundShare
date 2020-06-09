import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FriendsService } from 'src/app/services/friends.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { User } from 'src/app/models/entities/user/user.model';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  loggedInUser: User;
  clickedUser;
  username;
  friends = [];
  message;
  requests;
  invites=[];
  activeFriendships;
  isUserInvited: boolean = false;
  isUserARequester: boolean = false;
  isUserAFriend: boolean = false;
  isDataAvailable: boolean = false;
  constructor(private friendService: FriendsService, private activeRoute: ActivatedRoute, private tokenStorage: TokenStorageService) {
  }

  ngOnInit() {
    const username = this.activeRoute.snapshot.paramMap.get('username');
    this.loggedInUser = this.tokenStorage.getUser();
    this.getUser(username);
    console.log(username);
    this.getClickedUserFriends(username);
    this.getRequestFromUser(username);
    this.getInviteToUser(username);
  }

  public getUser(username){
    this.friendService.getUser(username).subscribe(
      (user) => {this.clickedUser = user;
               this.isDataAvailable = true;
      });
  }

  public getClickedUserFriends(username){
    this.friendService.getClickedUserFriends(username).subscribe(
      data => {this.friends = data
               for( let rel of this.friends){
                 if(rel.requester.username === this.loggedInUser.username || rel.friend.username === this.loggedInUser.username){
                   this.isUserAFriend = true;
                 }
                }

      });
  }

  public getRequestFromUser(username){
    this.friendService.getRequestFromUser(this.loggedInUser.username).subscribe(
      data => {this.requests = data
               for(let request of this.requests){
                  if(request.requester.username === this.clickedUser.username) {
                    this.isUserARequester = true;
                  }
               }
          });
  }

  public getInviteToUser(username){
    this.friendService.getInvitesToUser(username).subscribe(
      data => {this.invites = data;
               for(let invite of this.invites){
                 console.log(invite.requester);
                 if(invite.requester.username === this.loggedInUser.username){
                      this.isUserInvited = true;
                      break;
                  }
               }
      });
  }

  invite() {
    this.friendService.invite(this.clickedUser.user_id).subscribe(
      data => this.message = data
    );
  }




}
