package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.mleczkomatyaszek.SoundShare.Entity.Playlist;
import pl.mleczkomatyaszek.SoundShare.Model.PlaylistModel;
import pl.mleczkomatyaszek.SoundShare.Service.PlaylistService;
import pl.mleczkomatyaszek.SoundShare.Service.SongService;
import pl.mleczkomatyaszek.SoundShare.Service.UserService;

import java.security.Principal;
import java.util.Optional;

@RestController
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/playlists")
    public Page<Playlist> findAllPlaylists(Optional<String> title, Pageable pageable){
        return playlistService.findAllPlaylists(title,pageable);
    }

    @GetMapping("/playlists/{id}")
    public Playlist findPlaylist(@PathVariable Long id){
        return playlistService.findById(id);
    }

    @PostMapping("/playlists")
    public Playlist savePlaylist(@RequestBody PlaylistModel model, Principal principal){
        return playlistService.savePlaylist(model, principal);
    }

    @PutMapping("/playlists")
    public Playlist editPlaylist(@RequestBody PlaylistModel model){
        return playlistService.editPlaylist(model);
    }

    @DeleteMapping("/playlists/{id}")
    public String deletePlaylist(@PathVariable Long id){
        return  playlistService.deletePlaylist(id);
    }

}

