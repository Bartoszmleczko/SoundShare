package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.stereotype.Service;
import pl.mleczkomatyaszek.SoundShare.Entity.Playlist;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Repository.PlaylistRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Transactional
    public List<Playlist> findAllPlaylists(){
        return playlistRepository.findAll();
    }

    @Transactional
    public Playlist findById(Long id){
        return playlistRepository.findById(id).orElseThrow(() ->new GenericIdNotFoundException(Playlist.class.getSimpleName(),id));
    }


}
