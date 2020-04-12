package pl.mleczkomatyaszek.SoundShare.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "post_description")
    private String postDescription;

}
