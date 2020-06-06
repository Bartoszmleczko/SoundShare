package pl.mleczkomatyaszek.SoundShare.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    @Column(name = "path")
    private String path;

    @Column(name = "rate")
    private Double rate;

    @NonNull
    @Column(name = "lyrics",columnDefinition = "LONGTEXT")
    private String lyrics;

    @Column(name = "ratings")
    @ElementCollection(targetClass=Integer.class)
    private List<Integer> ratings = new ArrayList<>();

    @NonNull
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "likes")
    @ElementCollection(targetClass = String.class)
    private Set<String> likes = new HashSet<>();

    @OneToMany(mappedBy = "song", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonBackReference
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(mappedBy = "songs")
    @JsonBackReference
    private List<Playlist> playlists;

    public void setRate(OptionalDouble average) {
        if(average.isPresent())
        this.rate = average.getAsDouble();
    }
}
