package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;

import java.util.List;

@CrossOrigin(allowedHeaders = "*", origins = "*")
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("select s from Song s where title like %?1% ")
    public Page<Song> findByTitle(String title, Pageable pageable);

}
