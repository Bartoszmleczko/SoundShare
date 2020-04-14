package pl.mleczkomatyaszek.SoundShare.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "song")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Song {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long song_id;

    @NonNull
    @Column(name="title")
    private String title;

    @NonNull
    @Column(name = "file_path")
    private String filePath;

    @NonNull
    @Column(name = "rate")
    private Integer rate;

    @OneToMany(mappedBy = "song", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonBackReference
    private List<Post> posts = new ArrayList<>();

}
