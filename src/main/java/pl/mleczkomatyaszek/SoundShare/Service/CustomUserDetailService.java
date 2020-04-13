package pl.mleczkomatyaszek.SoundShare.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.mleczkomatyaszek.SoundShare.Entity.Role;
import pl.mleczkomatyaszek.SoundShare.Entity.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);
        List<GrantedAuthority> roles = getUserAuthority(user.getRoles());

        return buildUserForAuthentication(user,roles);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> roles){

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(authorities);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities){
        return  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                true,true,true,true,authorities);
    }


}
