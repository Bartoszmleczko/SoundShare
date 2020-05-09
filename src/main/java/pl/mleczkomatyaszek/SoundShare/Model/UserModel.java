package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserModel {

    private String username;
    
    private String password;

    private String firstName;

    private String lastName;

    private String email;

}
