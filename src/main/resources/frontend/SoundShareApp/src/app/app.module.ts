import { Role } from './models/entities/role/role.model';
import { AuthGuardService } from './services/auth-guard.service';
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
import { NewSongComponent } from './songs/new-song/new-song.component';
import {FileUploadModule} from 'ng2-file-upload';
import { AuthorizationService } from './services/authorization.service';
import { AuthInterceptorService } from './services/auth-interceptor.service';
import { TokenStorageService } from './services/token-storage.service';
import { SongService } from './services/song.service';

const appRoutes: Routes =[
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: '', component: UserProfileComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}, children: [
    {path: 'songs', component: SongComponent, canActivate: [AuthGuardService], data: {roles: 'USER'},
      children: [
        {path: 'songForm', component: NewSongComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}}
      ]},
    {path: 'posts', component: PostComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}}
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
    PostComponent,
    NewSongComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule.forRoot(),
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    FileUploadModule
  ],
  providers: [AuthorizationService,TokenStorageService, SongService,
 {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
