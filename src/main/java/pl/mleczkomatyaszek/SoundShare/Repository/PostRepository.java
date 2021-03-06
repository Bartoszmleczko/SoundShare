package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.mleczkomatyaszek.SoundShare.Entity.Post;

import java.util.List;

@CrossOrigin
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where postTitle like %?1%")
    public List<Post> findAllByPostTitle(String title);

}
