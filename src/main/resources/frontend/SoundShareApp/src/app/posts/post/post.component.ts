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
  isLikedByUser: boolean[] = [];
  user;
  constructor(private postService: PostService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.postService.getUsersPosts().subscribe(
      data => {this.posts = data;
               this.user = this.tokenStorage.getUser().username;
               for(let i=0; i<this.posts.length; i++){
                  if(this.posts[i].likes.includes(this.user)){
                    this.isLikedByUser.push(true);
                    this.posts.filter
                  } else{
                  this.isLikedByUser.push(false);
                }
      }
    });
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

  likePost(id){
    const post = this.posts[id].post_id;
    this.postService.addLike(post).subscribe(
      data => {
        this.isLikedByUser[id] = true;
      }
    );
  }

  dislikePost(id){
    const post = this.posts[id].post_id;
    this.postService.deleteLike(post).subscribe(
      data => {
        this.isLikedByUser[id] = false;
      }
    );
  }

}
