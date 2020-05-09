package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mleczkomatyaszek.SoundShare.Entity.Relationship;
import pl.mleczkomatyaszek.SoundShare.Entity.Role;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Exception.UserAlreadyExists;
import pl.mleczkomatyaszek.SoundShare.Model.UserModel;
import pl.mleczkomatyaszek.SoundShare.Repository.*;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
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
    public List<User> findFriends(Long id){

        User user = this.findById(id);
        List<Relationship> isInvited = relationshipRepository.findAll().stream().filter(x -> x.getFriend().equals(user)).filter(x -> x.isActive()).collect(Collectors.toList());
        List<User> users1 = new ArrayList<>();
        for(Relationship r : isInvited){
            users1.add(r.getFriend());
        }
        List<Relationship> isRequester = relationshipRepository.findAll().stream().filter(x -> x.getRequester().equals(user)).filter(x -> x.isActive()).collect(Collectors.toList());
        for(Relationship r : isRequester){
            users1.add(r.getRequester());
        }
        return users1;


    }

}
