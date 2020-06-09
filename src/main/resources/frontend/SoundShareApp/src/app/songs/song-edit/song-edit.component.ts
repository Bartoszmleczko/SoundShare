import { Component, OnInit } from '@angular/core';
import { SongService } from 'src/app/services/song.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-song-edit',
  templateUrl: './song-edit.component.html',
  styleUrls: ['./song-edit.component.css']
})
export class SongEditComponent implements OnInit {

  title = '';
  lyrics = '';
  song;
  constructor(private songService: SongService, private activeRoute: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.songService.getSongById(this.activeRoute.snapshot.paramMap.get('id')).subscribe(
      data => {
        this.song = data;
        this.title = this.song.title;
        this.lyrics = this.song.lyrics;
      });
  }

  edit(){
    const editedSong = {song_id: this.song.song_id, title: this.title, lyrics: this.lyrics, ratings: this.song.ratings};
    this.songService.update(editedSong).subscribe(
      data => console.log(data)
    );
    this.router.navigate(['/songs']);
  }

}
