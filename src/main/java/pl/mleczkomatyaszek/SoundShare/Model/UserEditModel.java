package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserEditModel {

    private String username;

    private String firstName;

    private String lastName;

    private String email;
}
