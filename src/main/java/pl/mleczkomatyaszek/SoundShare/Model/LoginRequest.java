package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class LoginRequest {

    private String username;
    private String password;

}
