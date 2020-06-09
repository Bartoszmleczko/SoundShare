import { FormBuilder } from '@angular/forms';
import { map } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';

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
  constructor(private activeRoute: ActivatedRoute, private postService: PostService, private fb: FormBuilder) {}

  ngOnInit() {
    this.post = this.postService.getPost(this.activeRoute.snapshot.paramMap.get('id')).subscribe(
        data => this.post = data
    );
    this.postService.getComments(this.activeRoute.snapshot.paramMap.get('id')).subscribe(
      data => this.comments = data
    );
  }

  addComment(){
    const comment = {content: this.commentForm.get('content').value, postId: this.activeRoute.snapshot.paramMap.get('id')};
    this.postService.addComment(comment).subscribe(
      data => this.comments.push(data)
    );
  }

}