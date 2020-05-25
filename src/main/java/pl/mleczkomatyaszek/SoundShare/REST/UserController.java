package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.mleczkomatyaszek.SoundShare.Entity.Comment;
import pl.mleczkomatyaszek.SoundShare.Entity.Post;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Model.LoginRequest;
import pl.mleczkomatyaszek.SoundShare.Model.LoginResponse;
import pl.mleczkomatyaszek.SoundShare.Model.UserModel;
import pl.mleczkomatyaszek.SoundShare.Security.JwtUtils;
import pl.mleczkomatyaszek.SoundShare.Service.SongService;
import pl.mleczkomatyaszek.SoundShare.Service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class UserController {


    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final SongService songService;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, SongService songService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.songService = songService;
        this.jwtUtils = jwtUtils;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public Page<User> findAllUsers(Pageable pageable){
        return userService.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/users/{username}")
    public ResponseEntity<User> findUser (@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/users/{username}/songs")
    public List<Song> userSongs(@PathVariable String username, Pageable pageable){
        User user = userService.findByUsername(username);
        return songService.findAll(java.util.Optional.of("_"),pageable).stream().filter(x ->
            x.getFilePath().contains(user.getUsername())).collect(Collectors.toList());
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetails user = (UserDetails) authentication.getPrincipal();
        User dbUser = userService.findByUsername(user.getUsername());
        List<String> roles = user.getAuthorities().stream().map(item -> ((GrantedAuthority) item).getAuthority()).collect(Collectors.toList());
        String username = user.getUsername();
        String email = dbUser.getEmail();
        LoginResponse res = new LoginResponse(username, email, roles,jwt);
        return ResponseEntity.ok(res);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@Valid @RequestBody UserModel userModel){
        return userService.saveUser(userModel);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/users/{id}/relationships")
    public List<User> findFriends(@PathVariable Long id){
        return userService.findFriends(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/users/{username}/posts")
    public List<Post> getUsersPosts(@PathVariable String username){
        return userService.findByUsername(username).getPosts();
    }

}
