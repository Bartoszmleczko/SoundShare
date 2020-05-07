import { HttpInterceptorService } from './services/http-interceptor.service';
import { Role } from './models/entities/role/role.model';
import { AuthGuardService } from './services/auth-guard.service';
import { LoginService } from './services/login.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule,ReactiveFormsModule} from '@angular/forms';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {Routes, RouterModule} from '@angular/router';
import { UserProfileComponent } from './users/user-profile/user-profile.component';
import { RegisterComponent } from './register/register.component';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { SongComponent } from './songs/song/song.component';
import { PostComponent } from './posts/post/post.component';

const appRoutes: Routes =[
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: '', component: UserProfileComponent, canActivate: [AuthGuardService], data: {roles: "USER"},children:[
    {path: 'songs', component: SongComponent, canActivate: [AuthGuardService], data: {roles: "USER"}},
    {path: 'posts', component: PostComponent, canActivate: [AuthGuardService], data: {roles: "USER"}}
  ]
}
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserProfileComponent,
    RegisterComponent,
    SongComponent,
    PostComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule.forRoot(),
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule
  ],
  providers: [LoginService, 
  {provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
