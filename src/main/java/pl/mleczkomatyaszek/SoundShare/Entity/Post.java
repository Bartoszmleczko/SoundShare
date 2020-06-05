package pl.mleczkomatyaszek.SoundShare.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long post_id;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_description",columnDefinition = "LONGTEXT")
    private String postDescription;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonManagedReference(value = "posts")
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Song song;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonBackReference("comments")
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "likes")
    @ElementCollection(targetClass=String.class)
    private Set<String> likes = new HashSet<>();
}
