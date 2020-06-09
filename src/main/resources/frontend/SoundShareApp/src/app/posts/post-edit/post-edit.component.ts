import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-post-edit',
  templateUrl: './post-edit.component.html',
  styleUrls: ['./post-edit.component.css']
})
export class PostEditComponent implements OnInit {

  post;

  postForm = this.fb.group({
        title: [''],
        description: ['']
      });
  isDataLoaded = false;
  constructor(private postService: PostService, private tokenStorage: TokenStorageService, private activeRoute: ActivatedRoute,
              private fb: FormBuilder, private router: Router) { }

  ngOnInit() {
      this.postService.getPost(this.activeRoute.snapshot.paramMap.get('id')).subscribe(
        data => {
          this.post = data;
          console.log(this.post);
          this.isDataLoaded = true;
        }
      );
  }

  editPost(){
    const newPost = {postId: this.post.post_id, title: this.post.postTitle, description: this.post.postDescription,likes: this.post.likes};
    this.postService.editPost(newPost).subscribe(
      data => console.log(data)
    );
    this.router.navigate(['/posts']);
  }



}
