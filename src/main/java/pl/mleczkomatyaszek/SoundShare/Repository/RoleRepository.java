package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.mleczkomatyaszek.SoundShare.Entity.Role;

@CrossOrigin
public interface RoleRepository extends JpaRepository<Role,Long> {

    public Role findByName(String name);

}
