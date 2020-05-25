import { User } from './../../models/entities/user/user.model';
import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  currentUser: User;
  constructor( private tokenStorage: TokenStorageService, private router: Router) {}

  ngOnInit() {
    console.log(sessionStorage);
    
  }

  logout(){
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  }

}
