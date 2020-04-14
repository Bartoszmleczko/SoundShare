package pl.mleczkomatyaszek.SoundShare.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "song", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonBackReference
    private List<Post> posts = new ArrayList<>();

}
