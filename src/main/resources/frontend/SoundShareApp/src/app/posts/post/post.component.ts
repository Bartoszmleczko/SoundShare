import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  posts;

  constructor(private postService: PostService) { }

  ngOnInit() {
    this.postService.getUsersPosts().subscribe(
      data => this.posts = data
    );
    console.log(this.posts);
  }

}
