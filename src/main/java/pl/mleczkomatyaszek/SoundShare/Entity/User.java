package pl.mleczkomatyaszek.SoundShare.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Size(min = 3,max = 25)
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name="username")
    private String username;

    @Size(min = 3,max = 25)
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name = "password")
    private String password;

    @Size(min = 3,max = 25)
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name="first_name")
    private String firstName;

    @Size(min = 3,max = 25)
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name="last_name")
    private String lastName;

    @Email
    @NotNull(message="This field cannot be empty")
    @NotEmpty(message="This field cannot be empty")
    @Column(name = "email")
    private String email;


}
