import { FormBuilder } from '@angular/forms';
import { map } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit {

  post;
  comments;
  commentForm = this.fb.group({
    content: ['']
  });
  songUser;
  likes: [];
  isPostAvailable = false;
  isCommentAvailable = false;
  isLikedByUser = false;
  loggedInUser;
  constructor(private activeRoute: ActivatedRoute, private postService: PostService, private fb: FormBuilder,
              private tokenStorage: TokenStorageService) {}

  ngOnInit() {
    this.post = this.postService.getPost(this.activeRoute.snapshot.paramMap.get('id')).subscribe(
        data =>{
        this.loggedInUser = this.tokenStorage.getUser();
        this.post = data;
        this.likes = this.post.likes;
        for(let like of this.likes){
          if(like === this.loggedInUser.username){
            this.isLikedByUser = true;
          }
        }
        this.songUser = this.post.song.user;
        console.log(this.post);
        this.isPostAvailable = true;
        } 

    );
    this.postService.getComments(this.activeRoute.snapshot.paramMap.get('id')).subscribe(
      data =>{
        this.comments = data;
        this.isCommentAvailable = true;
      }

    );
  }

  addComment(){
    const comment = {content: this.commentForm.get('content').value, postId: this.activeRoute.snapshot.paramMap.get('id')};
    this.postService.addComment(comment).subscribe(
      data => this.comments.push(data)
    );
  }

  deleteComment(id){
    const comment = this.comments[id].comment_id;
    this.postService.deleteComment(comment).subscribe(
      data => console.log(data)
    );
    this.comments.splice(id,1)
  }

  like(){
    this.postService.addLike(this.post.post_id).subscribe(
      data => {
        console.log(data);
        this.isLikedByUser =true;
      }
    );
    }

dislike(){
  this.postService.deleteLike(this.post.post_id).subscribe(
    data => {
      console.log(data);
      this.isLikedByUser = false;
    }
  );
}

}
