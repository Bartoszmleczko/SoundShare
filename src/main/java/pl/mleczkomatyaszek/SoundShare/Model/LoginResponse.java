package pl.mleczkomatyaszek.SoundShare.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mleczkomatyaszek.SoundShare.Entity.Role;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String email;
    private List<String> roles;
    private String token;

}
