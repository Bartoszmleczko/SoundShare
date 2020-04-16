package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczkomatyaszek.SoundShare.Entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
