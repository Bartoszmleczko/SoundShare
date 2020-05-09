package pl.mleczkomatyaszek.SoundShare.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference(value = "requests")
    @JsonIgnore
    private User requester;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    @JsonManagedReference(value = "friends")
    @JsonIgnore
    private User  friend;

    @Column(name = "is_active")
    private boolean isActive;

}
