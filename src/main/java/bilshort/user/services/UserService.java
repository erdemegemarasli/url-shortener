package bilshort.user.services;

import bilshort.user.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();
    User getUserById(Integer id);
    Long deleteUserById(Integer id);

    UserDetails loadUserByUsername(String username);
    UserDetails loadUserByApiKey(String apiKey);
    UserDetails loadUserByEmail(String email);

    User save(User user);
    User save(User user, Boolean isPasswordChanged);
    User save(User user, Boolean isAdmin, Boolean isPasswordChanged);

    User getUserByUserName(String userName);
}
