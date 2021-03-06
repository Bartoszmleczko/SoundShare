package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import pl.mleczkomatyaszek.SoundShare.Entity.Post;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Model.LikeModel;
import pl.mleczkomatyaszek.SoundShare.Model.PostModel;
import pl.mleczkomatyaszek.SoundShare.Repository.PostRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final SongService songService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService, SongService songService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.songService = songService;
    }

    @Transactional
    public List<Post> findAllPosts(Optional<String> title){
        return postRepository.findAllByPostTitle(title.orElse("_"));
    }

    @Transactional
    public Post findById(Long id){
        return postRepository.findById(id).orElseThrow(() -> new GenericIdNotFoundException(Post.class.getSimpleName(),id));
    }

    @Transactional
    public Post save(PostModel model, Principal principal){

        Post post = new Post();
        User user = new User();
        user = userService.findByUsername(principal.getName());
        Song song  = new Song();
        song = songService.findById(model.getSongId());
        post.setPostTitle(model.getTitle());
        post.setPostDescription(model.getDescription());
        post.setSong(song);
        post.setUser(user);
        post.setDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Transactional
    public Post edit (PostModel model){

        Post post = this.findById(model.getPostId());
        post.setPostDescription(model.getDescription());
        post.setPostTitle(model.getTitle());
        post.setDate(LocalDateTime.now());
        post.setLikes(model.getLikes());
        return postRepository.save(post);
    }

    @Transactional
    public String delete(Long id){
        Post post = new Post();
        post = this.findById(id);
        postRepository.delete(post);
        return "Post deleted";
    }

    @Transactional
    public Post addLike(LikeModel model){
        Post post = this.findById(model.getPostId());
        User user = userService.findByUsername(model.getLike());
        Set<String> likes = post.getLikes();
        likes.add(user.getUsername());
        post.setLikes(likes);
        return postRepository.save(post);
    }

    @Transactional
    public Post deleteLike(LikeModel model){
        Post post = this.findById(model.getPostId());
        User user = userService.findByUsername(model.getLike());
        Set<String> likes = post.getLikes();
        likes.remove(user.getUsername());
        post.setLikes(likes);
        return postRepository.save(post);
    }

}
