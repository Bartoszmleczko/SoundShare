package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczkomatyaszek.SoundShare.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
