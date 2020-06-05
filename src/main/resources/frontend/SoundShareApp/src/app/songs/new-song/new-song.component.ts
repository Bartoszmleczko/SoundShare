
import { Component, OnInit, EventEmitter } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { FileUploader } from 'ng2-file-upload';
import { SongService } from 'src/app/services/song.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-new-song',
  templateUrl: './new-song.component.html',
  styleUrls: ['./new-song.component.css']
})
export class NewSongComponent implements OnInit {
  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  title = '';
  lyrics = '';
  fileInfos: Observable<any>;


  constructor( private fb: FormBuilder,  private router: Router, private songService: SongService) {

   }

  ngOnInit() {

  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }


  upload() {
    this.progress = 0;
  
    this.currentFile = this.selectedFiles.item(0);
    this.songService.upload(this.currentFile,this.title, this.lyrics).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          console.log(event);
        }
      },
      err => {
        this.progress = 0;
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;
      });
  
    this.selectedFiles = undefined;
    this.router.navigate(['/songs']);
  }

}
