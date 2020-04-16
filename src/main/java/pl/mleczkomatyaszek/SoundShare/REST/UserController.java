package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Service.SongService;
import pl.mleczkomatyaszek.SoundShare.Service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;
    private final SongService songService;

    @Autowired
    public UserController(UserService userService, SongService songService) {
        this.userService = userService;
        this.songService = songService;
    }

    @GetMapping("/users")
    public Page<User> findAllUsers(Pageable pageable){
        return userService.findAll(pageable);
    }

    @GetMapping("/users/{id}")
    public User findUser (@PathVariable Long id){
        return userService.findById(id);
    }

    @GetMapping("/users/{id}/songs")
    public List<Song> userSongs(@PathVariable Long id, Pageable pageable){
        User user = userService.findById(id);
        return songService.findAll(null,pageable).stream().filter(x ->
            x.getFilePath().contains(user.getUsername())).collect(Collectors.toList());
    }


    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@Valid @RequestBody User user){
        return userService.saveUser(user);
    }


}
