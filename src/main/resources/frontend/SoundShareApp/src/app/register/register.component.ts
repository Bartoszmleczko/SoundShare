import { User } from './../models/entities/user/user.model';
import { FormBuilder,FormGroup,FormControl,ReactiveFormsModule, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { RegisterService } from '../services/register.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [RegisterService]
})
export class RegisterComponent implements OnInit {
  newUser: User;
  registerForm = this.fb.group({
    username: ['',[Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(3)]],
    firstName: ['', [Validators.required, Validators.minLength(3)]],
    lastName: ['', [Validators.required, Validators.minLength(3)]],
    email: ['',[Validators.required]]
  });

  constructor(private fb: FormBuilder, private registerService: RegisterService, private router: Router) {
  }

  ngOnInit() {
  }

  register() {
    this.newUser = new User(null,this.registerForm.get('username').value,
      this.registerForm.get('password').value,
      this.registerForm.get('firstName').value,
      this.registerForm.get('lastName').value,
      this.registerForm.get('email').value);
    this.registerService.register(this.newUser);
    this.router.navigate(['/login']);
  }

}
