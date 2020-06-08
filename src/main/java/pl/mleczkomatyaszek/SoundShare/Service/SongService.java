package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import pl.mleczkomatyaszek.SoundShare.Entity.Playlist;
import pl.mleczkomatyaszek.SoundShare.Entity.Post;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Exception.WrongFileFormatException;
import pl.mleczkomatyaszek.SoundShare.Model.LikeModel;
import pl.mleczkomatyaszek.SoundShare.Model.SongModel;
import pl.mleczkomatyaszek.SoundShare.Repository.PlaylistRepository;
import pl.mleczkomatyaszek.SoundShare.Repository.PostRepository;
import pl.mleczkomatyaszek.SoundShare.Repository.SongRepository;
import org.springframework.mock.web.MockMultipartFile;
import javax.transaction.Transactional;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final PostRepository postRepository;
    private final PlaylistRepository playlistRepository;

    @Autowired
    public SongService(SongRepository songRepository, UserService userService, FileStorageService fileStorageService, PostRepository postRepository, PlaylistRepository playlistRepository) {
        this.songRepository = songRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.postRepository = postRepository;
        this.playlistRepository = playlistRepository;
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
    public Song save(MultipartFile file,String title, String lyrics, MultipartFile img, Principal principal) throws MalformedURLException {

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);
        if(!suffix.equals(".mp3") && !suffix.equals(".wav"))
            throw new WrongFileFormatException();

        String suffix2 = img.getOriginalFilename().substring(img.getOriginalFilename().length()-4);
        if(!suffix2.equals(".jpg") && !suffix2.equals(".png"))
            throw new WrongFileFormatException();

        User user = userService.findByUsername(principal.getName());
        fileStorageService.store(file,user.getUsername());
        fileStorageService.store(img,user.getUsername());
        Path path = Paths.get(user.getUserPath()+ File.separator + file.getOriginalFilename());
        Path imgPath = Paths.get(user.getUserPath()+ File.separator + img.getOriginalFilename());
        String relPath =  path.toString().substring(path.toString().indexOf("media")+5);
        String relPath2 = imgPath.toString().substring(imgPath.toString().indexOf("media")+5);
        String url = "http://127.0.0.1:8887" + relPath.replaceAll("\\\\","/");
        String url2 = "http://127.0.0.1:8887" + relPath2.replaceAll("\\\\","/");
        Song song = new Song(title, url, path.toString(), lyrics ,LocalDateTime.now(),url2,imgPath.toString());

        return songRepository.save(song);
    }

    @Transactional
    public Song edit(SongModel model){
        Song song = this.findById(model.getSong_id());

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

        List<Playlist> playlists = song.getPlaylists();
        List<Playlist> newPlaylists = new ArrayList<>();

        for(int i =0 ; i<playlists.size();i++){
            playlists.get(i).getSongs().remove(song);
            playlistRepository.save(playlists.get(i));
        }
        File file = new File(song.getPath());
        file.delete();
        File img = new File(song.getImgFullPath());
        img.delete();
        songRepository.delete(song);
        return "Song deleted";
    }

    public Song addLike(LikeModel model){
        Song song = this.findById(model.getPostId());
        Set<String> likes = song.getLikes();
        likes.add(model.getLike());
        song.setLikes(likes);
        return songRepository.save(song);
    }

    public Song deleteLike(LikeModel model){
        Song song = this.findById(model.getPostId());
        Set<String> likes = song.getLikes();
        likes.remove(model.getLike());
        song.setLikes(likes);
        return songRepository.save(song);
    }

}
