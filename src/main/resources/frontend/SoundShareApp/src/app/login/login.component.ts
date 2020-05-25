
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder,ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthorizationService } from '../services/authorization.service';
import { TokenStorageService } from '../services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLoggedIn = false;
  isLoginFailed = false;

  userForm = this.fb.group({
      username : [''],
      password : ['']
  });

  constructor(private fb: FormBuilder, private authService: AuthorizationService, private router: Router, private tokenStorage: TokenStorageService) { 
   }

  ngOnInit() {
    console.log(sessionStorage);
    
  }

  authenticate(){
    let username = this.userForm.get('username').value;
    let password = this.userForm.get('password').value;
    this.authService.login({username, password}).subscribe(
      data => {
        this.tokenStorage.saveUser(data);
        this.tokenStorage.saveJwtToken(data.token);
        this.router.navigate(['']);
      }
    );
  }

}
