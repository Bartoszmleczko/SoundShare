import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { SongService } from 'src/app/services/song.service';

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
  isDataAvailable = false;
  placeholder = 'Type in Your post description';
  
  constructor(private postService: PostService, private fb: FormBuilder, private activeRoute: ActivatedRoute, private songService: SongService) { }

  ngOnInit() {
    const song_id= this.activeRoute.snapshot.paramMap.get('song_id');
    this.songService.getSongById(song_id).subscribe(
      data => {
        this.song = data;
        this.isDataAvailable = true;
      }
    );

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
