import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { SongService } from 'src/app/services/song.service';
import { FriendsService } from 'src/app/services/friends.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  imgUrl;
  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  loggedInUser;
  isDataAvailable = false;

  constructor(private userService: UserService, private friendService: FriendsService,  private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.getUser();
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  getUser(){
    const username = this.tokenStorage.getUser().username;
    this.friendService.getUser(username).subscribe(
      data => {
        this.isDataAvailable=true;
        this.loggedInUser = data;
      });
  }

  upload(){
    this.progress = 0;
  
    this.currentFile = this.selectedFiles.item(0);
    this.userService.upload(this.currentFile).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          console.log(event);
          this.imgUrl = event.body;
        }
      },
      err => {
        this.progress = 0;
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;
      });
  }

}
