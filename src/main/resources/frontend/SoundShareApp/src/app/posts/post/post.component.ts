import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostService } from 'src/app/services/post.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  posts;
  isLikedByUser: boolean = false;

  constructor(private postService: PostService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.postService.getUsersPosts().subscribe(
      data => this.posts = data
    );
  }

  deletePost(id){
    const post_id = this.posts[id].post_id;
    this.postService.deletePost(post_id).subscribe(
      data =>{
        console.log(data);
        this.posts.splice(id,1);
      } 
    );
  }

}
