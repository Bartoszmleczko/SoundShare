package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.mleczkomatyaszek.SoundShare.Entity.User;

import java.util.List;

@CrossOrigin
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where username like %?1%")
    public List<User> findAllByUsername(String username);

    public User findByUsername(String username);

}
