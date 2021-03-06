package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Model.LikeModel;
import pl.mleczkomatyaszek.SoundShare.Model.SongModel;
import pl.mleczkomatyaszek.SoundShare.Repository.SongRepository;
import pl.mleczkomatyaszek.SoundShare.Service.FileStorageService;
import pl.mleczkomatyaszek.SoundShare.Service.SongService;

import javax.print.attribute.standard.Media;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
public class SongController {

    private final SongService songService;


    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/songs")
    public Page<Song> findAll(Optional<String> title, Pageable pageable){
        return songService.findAll(title,pageable);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/songs/{id}")
    public Song findById( @PathVariable Long id){
        return songService.findById(id);
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/songs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Song uploadSong(@RequestParam(value = "file")  MultipartFile file,@RequestParam("title") String title,
                           @RequestParam("lyrics") String lyrics,@RequestParam("img") MultipartFile img, Principal principal) throws MalformedURLException {

            return songService.save(file,title,lyrics,img,principal);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/songs")
    public Song song(@RequestBody SongModel model){
        return songService.edit(model);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/songs/{id}")
    public String delete(@PathVariable Long id){
        return songService.delete(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/songs/likes")
    public Song addLike(@RequestBody LikeModel model){
        return  songService.addLike(model);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/songs/dislikes")
    public Song deleteLike(@RequestBody LikeModel model){
        return  songService.deleteLike(model);
    }



}
