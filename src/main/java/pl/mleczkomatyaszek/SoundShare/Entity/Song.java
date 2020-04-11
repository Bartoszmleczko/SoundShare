package pl.mleczkomatyaszek.SoundShare.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "song")
@Data
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long song_id;

    @Column(name="title")
    private String title;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "rate")
    private Integer rate;

}
