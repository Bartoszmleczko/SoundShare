package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczkomatyaszek.SoundShare.Entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

}
