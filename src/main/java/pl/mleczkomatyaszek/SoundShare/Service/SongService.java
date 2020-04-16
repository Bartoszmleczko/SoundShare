package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczkomatyaszek.SoundShare.Entity.Post;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Exception.WrongFileFormatException;
import pl.mleczkomatyaszek.SoundShare.Model.SongModel;
import pl.mleczkomatyaszek.SoundShare.Repository.PostRepository;
import pl.mleczkomatyaszek.SoundShare.Repository.SongRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final PostRepository postRepository;

    @Autowired
    public SongService(SongRepository songRepository, UserService userService, FileStorageService fileStorageService, PostRepository postRepository) {
        this.songRepository = songRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.postRepository = postRepository;
    }

    @Transactional
    public Page<Song> findAll(Optional<String> title, Pageable pageable){
        return songRepository.findByTitle(title.orElse("_"),pageable);
    }

    @Transactional
    public Song findById(Long id){
        return songRepository.findById(id).orElseThrow(() -> new GenericIdNotFoundException(Song.class.getSimpleName(),id));
    }


    @Transactional
    public Song save(MultipartFile file, String title, Principal principal){

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);
        if(!suffix.equals(".mp3") && !suffix.equals(".wav"))
            throw new WrongFileFormatException();

        User user = userService.findByUsername(principal.getName());
        fileStorageService.store(file,user.getUsername());
        Path path = Paths.get(user.getUserPath()+ File.separator + file.getOriginalFilename());
        Song song = new Song(title,path.toString());
        return songRepository.save(song);
    }

    @Transactional
    public Song edit(SongModel model){
        Song song = this.findById(model.getSongId());


        song.setTitle(model.getTitle());
        song.setRatings(model.getRatings());
        song.setRate(model.getRatings().stream().mapToInt(Integer::intValue).average());
        return songRepository.save(song);
    }


    @Transactional
    public String delete(Long id){
        Song song = this.findById(id);

        for(Post p : song.getPosts()){
            p.setSong(null);
            postRepository.save(p);
        }

        File file = new File(song.getFilePath());
        file.delete();
        songRepository.delete(song);
        return "Song deleted";
    }


}
