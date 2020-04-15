package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.mleczkomatyaszek.SoundShare.Entity.Playlist;
import pl.mleczkomatyaszek.SoundShare.Service.PlaylistService;
import pl.mleczkomatyaszek.SoundShare.Service.SongService;
import pl.mleczkomatyaszek.SoundShare.Service.UserService;

import java.util.Optional;

@RestController
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("playlists")
    public Page<Playlist> findAllPlaylists(Optional<String> title, Pageable pageable){
        return playlistService.findAllPlaylists(title,pageable);
    }

    @GetMapping("/playlists/{id}")
    public Playlist findPlaylist(@PathVariable Long id){
        return playlistService.findById(id);
    }



}

