package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Model.SongModel;
import pl.mleczkomatyaszek.SoundShare.Repository.SongRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    public SongService(SongRepository songRepository, UserService userService, FileStorageService fileStorageService) {
        this.songRepository = songRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public List<Song> findAll(Optional<String> title, Sort sort){
        return songRepository.findByTitle(title.orElse("_"),sort);
    }

    @Transactional
    public Song findById(Long id){
        return songRepository.findById(id).orElseThrow(() -> new GenericIdNotFoundException(Song.class.getSimpleName(),id));
    }


    @Transactional
    public SongModel save(MultipartFile file, String title, Principal principal){

        User user = userService.findByUsername(principal.getName());
        fileStorageService.store(file,user.getUsername());
        Path path = Paths.get(user.getUserPath()+ File.separator + file.getOriginalFilename());

        return new SongModel(title,path.toString(),0);

    }

}
