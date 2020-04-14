package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;

public interface SongRepository extends JpaRepository<Song, Long> {

}
