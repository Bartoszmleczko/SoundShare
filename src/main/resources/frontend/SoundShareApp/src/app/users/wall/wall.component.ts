import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-wall',
  templateUrl: './wall.component.html',
  styleUrls: ['./wall.component.css']
})
export class WallComponent implements OnInit {

  user;
  wallPosts = [];
  postsLoaded: boolean = false;
  isLikedByUser: boolean[] = [];
  constructor(private postService: PostService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.postService.getFriendsPosts(this.tokenStorage.getUser().username).subscribe(
      (data: any[]) => {
        this.wallPosts = data;
        console.log(this.wallPosts[0].song);
        this.postsLoaded = true;
        this.user = this.tokenStorage.getUser().username;
        for(let i=0; i<this.wallPosts.length; i++){
          if(this.wallPosts[i].likes.includes(this.user)){
            this.isLikedByUser.push(true);
            this.wallPosts.filter
          } else{
            this.isLikedByUser.push(false);
          }
          }
        }
    );


  }


  like(list_id){
    const post_id = this.wallPosts[list_id].post_id;
    this.postService.addLike(post_id).subscribe(
      data => {
        this.isLikedByUser[list_id] = true;
      }
    );
  }

  dislike(list_id){
    const post_id = this.wallPosts[list_id].post_id;
    this.postService.deleteLike(post_id).subscribe(
      data => {
        this.isLikedByUser[list_id] = false;
      }
    );
  }


}
