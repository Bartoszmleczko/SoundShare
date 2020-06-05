import { map, catchError } from 'rxjs/operators';
import { FormBuilder } from '@angular/forms';
import { Component, OnInit, OnChanges } from '@angular/core';
import { SongService } from 'src/app/services/song.service';
import { pipe, throwError } from 'rxjs';
import { DomSanitizer } from '@angular/platform-browser';
import { PostService } from 'src/app/services/post.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-song',
  templateUrl: './song.component.html',
  styleUrls: ['./song.component.css']
})
export class SongComponent implements OnInit {


  songs;
  currentRate = [];

  constructor(private songService: SongService, private postService: PostService, private router: Router) { }

  ngOnInit() {
    this.songService.getSongs().subscribe(
      data => this.songs = data
    );
  }

  
  public deleteSong(id){

    const song = this.songs[id];
    this.songs.splice(id, 1);
    this.songs = [...this.songs];

    return this.songService.deleteSong(song.song_id).subscribe(
      data => console.log(data)
    );
  }

  selectSong(id: number) {
    const song = this.songs[id];
    this.postService.setSong(song);
    this.router.navigate(['/postsForm']);
  }

  countRate(i,j) {
    console.log(i + ' ' + j);
    const song = this.songs[i];
    song.ratings.push(this.currentRate);
    console.log(song.song_id);
    this.songService.update(song).subscribe(
      data => this.songs[i] = data
    );
  }

}
