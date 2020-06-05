import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css']
})
export class NewPostComponent implements OnInit {

  song;
  postForm = this.fb.group({
    title: [''],
    description: ['']
  });
  ckeConfig: any;

  constructor(private postService: PostService, private fb: FormBuilder) { }

  ngOnInit() {
    this.ckeConfig = {
      allowedContent: false,
      extraPlugins: 'divarea',
      forcePasteAsPlainText: true
    }; 
    this.song = this.postService.getSong();

  }

  addPost(){
    const post = {title: this.postForm.get('title').value, description: this.postForm.get('description').value,
     songId: this.song.song_id, likes: []};
    console.log(post);
    this.postService.addPost(post).subscribe(
      data => console.log(data)
    );
  }

}
