package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.mleczkomatyaszek.SoundShare.Entity.User;

@CrossOrigin
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
