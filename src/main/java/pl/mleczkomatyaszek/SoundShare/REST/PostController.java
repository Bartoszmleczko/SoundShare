package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import pl.mleczkomatyaszek.SoundShare.Entity.Post;
import pl.mleczkomatyaszek.SoundShare.Model.PostModel;
import pl.mleczkomatyaszek.SoundShare.Service.PostService;

import java.security.Principal;
import java.util.Optional;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public Page<Post> findAllPosts(Optional<String> title, Pageable pageable){
        return postService.findAllPosts(title,pageable);
    }

    @GetMapping("/posts/{id}")
    public Post findPost(@PathVariable Long id){
        return postService.findById(id);
    }

    @PostMapping("/posts")
    public Post savePost(@RequestBody PostModel post, Principal principal){

        return postService.save(post,principal);
    }

    @PutMapping("/posts")
    public Post editPost(@RequestBody PostModel model){
        return postService.edit(model);
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id){
        return postService.delete(id);
    }

}
