import { SongDetailComponent } from './songs/song-detail/song-detail.component';
import { PostService } from './services/post.service';
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
import { NewPostComponent } from './posts/new-post/new-post.component';
import {CKEditorModule} from 'ng2-ckeditor';
import { EditorModule } from "@tinymce/tinymce-angular";
import { PostDetailComponent } from './posts/post-detail/post-detail.component';
import { FriendsListComponent } from './friends/friends-list/friends-list.component';
import { SearchUsersComponent } from './friends/search-users/search-users.component';  
import { FriendsService } from './services/friends.service';
import { UserDetailComponent } from './users/user-detail/user-detail.component';
import { PostEditComponent } from './posts/post-edit/post-edit.component';
import { SongEditComponent } from './songs/song-edit/song-edit.component';
import { WallComponent } from './users/wall/wall.component';
import { EditUserComponent } from './users/edit-user/edit-user.component';


const appRoutes: Routes =[
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: '', component: UserProfileComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}, children: [
    {path: 'wall', component: WallComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'songs', component: SongComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'songs/:id', component: SongDetailComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'songs/edit/:id', component: SongEditComponent, canActivate: [AuthGuardService],data: {roles: 'USER'}},
    {path: 'songForm', component: NewSongComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'posts', component: PostComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'postsForm/:song_id', component: NewPostComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'posts/:id', component: PostDetailComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'posts/edit/:id', component: PostEditComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'friends', component: FriendsListComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'users/:username', component: UserDetailComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}},
    {path: 'profile', component: EditUserComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}}
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
    NewSongComponent,
    NewPostComponent,
    PostDetailComponent,
    FriendsListComponent,
    SearchUsersComponent,
    UserDetailComponent,
    PostEditComponent,
    SongEditComponent,
    WallComponent,
    EditUserComponent,
    SongDetailComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule.forRoot(),
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    FileUploadModule,
    CKEditorModule,
    EditorModule
  ],
  providers: [AuthorizationService, TokenStorageService, SongService, PostService, FriendsService,
 {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
