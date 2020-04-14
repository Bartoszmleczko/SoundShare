package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczkomatyaszek.SoundShare.Entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

    public Role findByName(String name);

}
