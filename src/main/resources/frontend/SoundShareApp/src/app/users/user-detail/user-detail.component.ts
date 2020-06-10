import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FriendsService } from 'src/app/services/friends.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { User } from 'src/app/models/entities/user/user.model';
import { SongService } from 'src/app/services/song.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  loggedInUser: User;
  clickedUser;
  username;
  friends = [];
  message;
  requests;
  invites=[];
  songs = [];
  posts = [];
  isLikedByUser: boolean[] = [];
  isPostLikedByUser: boolean[] = [];
  activeFriendships;
  isUserInvited: boolean = false;
  isUserARequester: boolean = false;
  isUserAFriend: boolean = false;
  isDataAvailable: boolean = false;
  isDataAvailable2: boolean = false;
  constructor(private friendService: FriendsService, private activeRoute: ActivatedRoute, private tokenStorage: TokenStorageService,
              private songService: SongService, private postService: PostService) {
  }

  ngOnInit() {
    const username = this.activeRoute.snapshot.paramMap.get('username');
    this.loggedInUser = this.tokenStorage.getUser();
    this.getUser(username);
    this.getUserSongs(username);
    this.getUserPosts(username);
    this.getClickedUserFriends(username);
    this.getRequestFromUser(username);
    this.getInviteToUser(username);
  }

  public getUser(username){
    this.friendService.getUser(username).subscribe(
      (user) => { this.clickedUser = user;
                  this.isDataAvailable = true;
      });
  }

  public getUserSongs(username){
    this.songService.getSongsFromUser(username).subscribe(
      (data:[]) => {
        this.songs = data;
        for(let i=0; i<this.songs.length; i++){
          if(this.songs[i].likes.includes(this.loggedInUser.username)){
            this.isLikedByUser.push(true);
            this.songs.filter
          } else{
            this.isLikedByUser.push(false);
          }
        this.isDataAvailable2 = true;
      }
    }
    );
  }
  
  getUserPosts(username){
    this.postService.getPosts(username).subscribe(
      (data:[]) => {
        this.posts = data;
        for(let i=0; i<this.posts.length; i++){
          if(this.posts[i].likes.includes(this.loggedInUser.username)){
            this.isPostLikedByUser.push(true);
            this.posts.filter
          } else{
            this.isPostLikedByUser.push(false);
          }
        this.isDataAvailable2 = true;
      }
      }
    );
  }

  public getClickedUserFriends(username){
    this.friendService.getClickedUserFriends(username).subscribe(
      data => {this.friends = data
               for( let rel of this.friends){
                 if(rel.username === this.loggedInUser.username){
                   this.isUserAFriend = true;
                 }
                }

      });
  }

  public getRequestFromUser(username){
    this.friendService.getRequestFromUser(this.loggedInUser.username).subscribe(
      data => {this.requests = data
               for(let request of this.requests){
                  if(request.requester.username === this.clickedUser.username) {
                    this.isUserARequester = true;
                    break;
                  }
               }
          });
  }

  public getInviteToUser(username){
    this.friendService.getInvitesToUser(username).subscribe(
      data => {this.invites = data;
               for(let invite of this.invites){
                 if(invite.requester.username === this.loggedInUser.username){
                      this.isUserInvited = true;
                      break;
                  }
               }
      });
  }

  invite() {
    this.friendService.invite(this.clickedUser.user_id).subscribe(
      data => this.message = data
    );
  }

  likeSong(id){
    this.songService.addLike(this.songs[id].song_id).subscribe(
      data =>  {
        this.isLikedByUser[id] = true;
      }
    );
    }

  dislikeSong(id){
    this.songService.deleteLike(this.songs[id].song_id).subscribe(
      data => {
        this.isLikedByUser[id] = false;
      }
    );
  }

  likePost(id){
    this.postService.addLike(this.posts[id].post_id).subscribe(
      data => {
        this.isPostLikedByUser[id] = true;
      }
    );
  }

  dislikePost(id){
    this.postService.deleteLike(this.posts[id].post_id).subscribe(
      data => {
        this.isPostLikedByUser[id] = false;
      }
    );
  }




  



}
