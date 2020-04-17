package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.mleczkomatyaszek.SoundShare.Entity.Comment;

@CrossOrigin
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
