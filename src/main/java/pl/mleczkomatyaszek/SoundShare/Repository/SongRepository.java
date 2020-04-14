package pl.mleczkomatyaszek.SoundShare.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.mleczkomatyaszek.SoundShare.Entity.Song;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("select s from Song s where title like %1 ")
    public List<Song> findByTitle(String title, Sort sort);

}
