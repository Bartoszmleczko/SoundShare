import { Component, OnInit } from '@angular/core';
import { FriendsService } from 'src/app/services/friends.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-friends-list',
  templateUrl: './friends-list.component.html',
  styleUrls: ['./friends-list.component.css']
})
export class FriendsListComponent implements OnInit {
  relations;
  friends =[];
  invites;
  isDataLoaded: boolean = false;
  resp;
  username = this.tokenStorage.getUser().username;
  constructor(private friendService: FriendsService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.friendService.getUsersFriends().subscribe(
      (data: []) => {
        this.friends = data;
        this.isDataLoaded = true;
      }

    );
    this.friendService.getInvitesToUser(this.tokenStorage.getUser().username).subscribe(
      data => this.invites = data
    );
  }



  acceptInvitation(id){
    const inv = this.invites[id];
    inv.active = true;
    this.friendService.acceptInvitation(inv).subscribe(
      data => {
        this.resp= data;
        this.invites.splice(id, 1);
        this.friends.push(this.resp.requester);
      }
    );
  }

}
