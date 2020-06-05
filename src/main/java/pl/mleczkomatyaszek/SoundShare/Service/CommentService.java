package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mleczkomatyaszek.SoundShare.Entity.Comment;
import pl.mleczkomatyaszek.SoundShare.Entity.Post;
import pl.mleczkomatyaszek.SoundShare.Entity.User;
import pl.mleczkomatyaszek.SoundShare.Exception.GenericIdNotFoundException;
import pl.mleczkomatyaszek.SoundShare.Model.CommentModel;
import pl.mleczkomatyaszek.SoundShare.Repository.CommentRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Transactional
    public Page<Comment> findAllComments(Pageable pageable){
        return commentRepository.findAll(pageable);
    }

    @Transactional
    public Comment findComment(Long id){
        return commentRepository.findById(id).orElseThrow(() -> new GenericIdNotFoundException(Comment.class.getSimpleName(),id));
    }

    @Transactional
    public Comment saveComment(CommentModel model, Principal principal){

        Comment comment = new Comment();
        User user = userService.findByUsername(principal.getName());
        Post post = postService.findById(model.getPostId());

        comment.setContent(model.getContent());
        comment.setUser(user);
        comment.setPost(post);
        comment.setDate(LocalDateTime.now());

        return commentRepository.save(comment);

    }

    @Transactional
    public Comment editComment(CommentModel model){

        Comment comment = new Comment();
        comment = this.findComment(model.getCommentId());
        comment.setContent(model.getContent());
        comment.setDate(LocalDateTime.now());
        return commentRepository.save(comment);

    }

    @Transactional
    public String deleteComment(Long id){

        Comment comment = new Comment();
        comment = this.findComment(id);
        commentRepository.delete(comment);
        return "Comment deleted";
    }

}
