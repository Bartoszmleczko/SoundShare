package pl.mleczkomatyaszek.SoundShare.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.mleczkomatyaszek.SoundShare.Entity.Comment;
import pl.mleczkomatyaszek.SoundShare.Model.CommentModel;
import pl.mleczkomatyaszek.SoundShare.Service.CommentService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public Page<Comment> findAllComments(Pageable pageable){
        return commentService.findAllComments(pageable);
    }

    @GetMapping("/comments/{id}")
    public Comment findComment(@PathVariable Long id){
        return commentService.findComment(id);
    }

    @PostMapping("/comments")
    public Comment saveComment(@Valid @RequestBody CommentModel comment, Principal principal){
        return commentService.saveComment(comment, principal);
    }

    @PutMapping("/comments")
    public Comment editComment(@Valid @RequestBody CommentModel model ){
        return commentService.editComment(model);
    }

    @DeleteMapping("/comments/{id}")
    public String deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
    }

}
