package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.stereotype.Service;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Repository.SongRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Transactional
    public List<Song> findAll(){
        return songRepository.findAll();
    }

    @Transactional
    public Song findById(Long id){
        return songRepository.findById(id).orElseThrow(() -> new GenericIdNotFoundException(Song.class.getSimpleName(),id));
    }

}
