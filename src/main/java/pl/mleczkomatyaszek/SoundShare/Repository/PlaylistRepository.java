package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.mleczkomatyaszek.SoundShare.Entity.Playlist;

@CrossOrigin
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("select p from Playlist p where playlistName like %?1%")
    public Page<Playlist> findAllByPlaylistName(String title, Pageable pageable);

}
