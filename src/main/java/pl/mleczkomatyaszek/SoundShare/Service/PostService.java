package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.stereotype.Service;
import pl.mleczkomatyaszek.SoundShare.Entity.Post;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public List<Post> findAllPosts(){
        return postRepository.findAll();
    }

    @Transactional
    public Post findById(Long id){
        return postRepository.findById(id).orElseThrow(() -> new GenericIdNotFoundException(Post.class.getSimpleName(),id));
    }

}
