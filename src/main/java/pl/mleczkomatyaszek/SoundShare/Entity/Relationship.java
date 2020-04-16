package pl.mleczkomatyaszek.SoundShare.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "relationship")
@Data
@NoArgsConstructor
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="relationship_id")
    private Long relationship_id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    @JsonBackReference
    private User requester;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User  friend;

    @Column(name = "is_active")
    private boolean isActive;

}
