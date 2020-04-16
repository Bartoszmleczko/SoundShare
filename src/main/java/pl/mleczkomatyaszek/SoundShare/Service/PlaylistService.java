package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mleczkomatyaszek.SoundShare.Entity.Playlist;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Model.PlaylistModel;
import pl.mleczkomatyaszek.SoundShare.Repository.PlaylistRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: add PUT and DELETE methods code and adding single song to playlist

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongService songService;
    private final UserService userService;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, SongService songService, UserService userService) {
        this.playlistRepository = playlistRepository;
        this.songService = songService;
        this.userService = userService;
    }

    @Transactional
    public Page<Playlist> findAllPlaylists(Optional<String> title, Pageable pageable){
        return playlistRepository.findAllByPlaylistName(title.orElse("_"), pageable);
    }

    @Transactional
    public Playlist findById(Long id){
        return playlistRepository.findById(id).orElseThrow(() ->new GenericIdNotFoundException(Playlist.class.getSimpleName(),id));
    }

    @Transactional
    public Playlist savePlaylist(PlaylistModel model, Principal principal){

        Playlist playlist = new Playlist();
        User user = new User();
        user = userService.findByUsername(principal.getName());
        playlist.setUser(user);
        playlist.setPlaylistName(model.getName());

        return playlistRepository.save(playlist);
    }

    @Transactional
    public Playlist editPlaylist(PlaylistModel model){

        Playlist playlist = new Playlist();
        playlist = this.findById(model.getPlaylistId());
        playlist.setPlaylistName(model.getName());
        List<Song> songs = new ArrayList<>();
        if(model.getSongs()!=null)
        for(Long s : model.getSongs()){
            songs.add(songService.findById(s));
        }
        playlist.setSongs(songs);
        return playlistRepository.save(playlist);
    }

    @Transactional
    public String deletePlaylist(Long id){

        Playlist playlist = new Playlist();
        playlist = this.findById(id);
        playlistRepository.delete(playlist);

        return "Playlist deleted";

    }

}
