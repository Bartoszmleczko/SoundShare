package pl.mleczkomatyaszek.SoundShare.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @NonNull
    @Size(min = 3,max = 25)
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name="username",unique = true)
    private String username;

    @NonNull
    @Size(min = 3,max = 120)
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name = "password", columnDefinition = "LONGTEXT")
    private String password;

    @NonNull
    @Size(min = 3,max = 25)
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name="first_name")
    private String firstName;

    @NonNull
    @Size(min = 3,max = 25)
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name="last_name")
    private String lastName;

    @NonNull
    @Email
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name = "email")
    private String email;


    @Column(name = "user_path")
    private String userPath;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true,fetch =FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "friend",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference("friends")
    public List<Relationship> friends = new ArrayList<>();

    @OneToMany(mappedBy = "requester",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference("friends")
    public List<Relationship> requests = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference("comments")
    public List<Comment> comments = new ArrayList<>();

}
