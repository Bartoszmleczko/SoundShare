import { Component, OnInit } from '@angular/core';
import { FriendsService } from 'src/app/services/friends.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-search-users',
  templateUrl: './search-users.component.html',
  styleUrls: ['./search-users.component.css']
})
export class SearchUsersComponent implements OnInit {

  users =[];
  isDataLoaded: boolean = false;
  constructor(private friendService: FriendsService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.friendService.getUsers('').subscribe(
      (data: []) => {
        this.users = data;
        this.isDataLoaded = true;
      }
    )
    console.log(this.users);
  }

  getUsers(event) {
    this.friendService.getUsers(event.target.value).subscribe(
      (data: []) => this.users = data
    );
}

}
