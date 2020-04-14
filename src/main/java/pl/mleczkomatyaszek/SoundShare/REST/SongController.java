package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Model.SongModel;
import pl.mleczkomatyaszek.SoundShare.Repository.SongRepository;
import pl.mleczkomatyaszek.SoundShare.Service.FileStorageService;
import pl.mleczkomatyaszek.SoundShare.Service.SongService;

import javax.print.attribute.standard.Media;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class SongController {

    private final SongService songService;


    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs")
    public List<Song> findAll(Optional<String> title, Sort sort){
        return songService.findAll(title,sort);
    }

    @GetMapping("/songs/{id}")
    public Song findById( @PathVariable Long id){
        return songService.findById(id);
    }


    @PostMapping(value = "/songs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SongModel uploadSong(@RequestParam("file") MultipartFile file , @RequestParam("title") String title,Principal principal){

            return songService.save(file,title,principal);
    }

}
