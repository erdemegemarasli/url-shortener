package bilshort.user.services;

import bilshort.user.models.AuthDTO;
import bilshort.user.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User createUser(AuthDTO authDTO);
    List<User> getAllUsers();
    User getUserById(Integer id);
    Long deleteUserById(Integer id);
    User updateUser(User user);

    UserDetails loadUserByUsername(String username);
    User save(User user);
    User save(User user, Boolean isAdmin);
}
