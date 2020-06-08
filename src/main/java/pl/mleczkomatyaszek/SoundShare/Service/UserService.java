package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import pl.mleczkomatyaszek.SoundShare.Entity.*;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Exception.UserAlreadyExists;
import pl.mleczkomatyaszek.SoundShare.Model.UserModel;
import pl.mleczkomatyaszek.SoundShare.Repository.*;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FileStorageService fileStorageService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SongRepository songRepository;
    private final SongService songService;
    private final RelationshipRepository relationshipRepository;



    public UserService(UserRepository userRepository, RoleRepository roleRepository, FileStorageService fileStorageService, BCryptPasswordEncoder bCryptPasswordEncoder, SongRepository songRepository, @Lazy SongService songService, RelationshipRepository relationshipRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.fileStorageService = fileStorageService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.songRepository = songRepository;
        this.songService = songService;
        this.relationshipRepository = relationshipRepository;
    }

    @Transactional
    public List<User> findAll(Optional<String> username, Principal principal){
        List<User> allUsers = userRepository.findAllByUsername(username.orElse("_"));
        User u = this.findByUsername(principal.getName());
        allUsers.remove(u);
        List<User> friends = this.findFriends(u.getUsername());
        allUsers.removeAll(friends);
        return allUsers;
    }

    @Transactional
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new GenericIdNotFoundException(User.class.getSimpleName(),id));
    }

    @Transactional
    public User saveUser(UserModel userModel){
        User user = new User();
        if(this.findByUsername(userModel.getUsername())!=null)
            throw new UserAlreadyExists();
        user.setUsername(userModel.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        Role role = roleRepository.findByName("USER");
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        fileStorageService.initDir(user.getUsername());
        user.setUserPath(fileStorageService.getUploadPath().toString());
        return userRepository.save(user);
    }

    @Transactional
    public String editUser(MultipartFile img, Principal principal) {
        String username = principal.getName();
        User user = this.findByUsername(username);
        Path imgPath = Paths.get(user.getUserPath()+ File.separator + img.getOriginalFilename());
        String relPath2 = imgPath.toString().substring(imgPath.toString().indexOf("media")+5);
        String url2 = "http://127.0.0.1:8887" + relPath2.replaceAll("\\\\","/");
        user.setImgUrl(url2);
        user.setImgPath(imgPath.toString());
        fileStorageService.store(img,username);
        userRepository.save(user);
        return user.getImgUrl();
    }

    @Transactional
    public String deleteUser(Long id){

        User user = this.findById(id);
        List<Song> songs = songRepository.findAll().stream().filter(x -> x.getFilePath().contains(user.getUsername())).collect(Collectors.toList());
        for(Song s : songs){
            songService.delete(s.getSong_id());
        }

        relationshipRepository.deleteAll(user.getRequests());
        relationshipRepository.deleteAll(user.getFriends());

        String path =user.getUserPath();
        File file = new File(path);
        file.delete();
        userRepository.delete(user);
        return "User deleted";
    }

    @Transactional
    public List<User> findFriends(String username){

        User user = this.findByUsername(username);
        List<Relationship> friends = user.getFriends().stream().filter(x -> x.isActive()).collect(Collectors.toList());
        List<User> users = new ArrayList<>();
        for( Relationship r: friends){
            users.add(r.getRequester());
        }
        List<Relationship> requests = user.getRequests().stream().filter(x -> x.isActive()).collect(Collectors.toList());
        for( Relationship r:requests) {
            users.add(r.getFriend());
        }
        return users;
    }

    @Transactional
    public List<Post> findFriendsPosts( String username){
        User user = this.findByUsername(username);
        List<Relationship> friends = user.getFriends().stream().filter(x -> x.isActive()).collect(Collectors.toList());
        List<Post> post1 = new ArrayList<>();
        for( Relationship r: friends){
            post1.addAll(r.getRequester().getPosts());
        }
        List<Relationship> requests = user.getRequests().stream().filter(x -> x.isActive()).collect(Collectors.toList());
        for(Relationship r : requests){
            post1.addAll(r.getFriend().getPosts());
        }
        post1.sort((o1, o2) -> {
           return  -o1.getDate().compareTo(o2.getDate());
        });
        return post1;
    }

}
