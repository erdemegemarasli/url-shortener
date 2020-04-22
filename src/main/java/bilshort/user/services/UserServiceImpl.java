package bilshort.user.services;

import bilshort.user.models.Role;
import bilshort.user.models.User;
import bilshort.user.repositories.RoleRepository;
import bilshort.user.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public Long deleteUserById(Integer id) {
        return userRepository.deleteByUserId(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);

        if (user == null)
            return null;

        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), true, true, true, true, authorities);
    }

    @Override
    public User save(User user) {
        Role userRole = roleRepository.findByRoleName("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword())).setRoles(new HashSet<>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }

    @Override
    public User save(User user, Boolean isAdmin) {
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName("USER"));

        if (isAdmin)
            roles.add(roleRepository.findByRoleName("ADMIN"));

        user.setPassword(passwordEncoder.encode(user.getPassword())).setRoles(roles);
        return userRepository.save(user);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new ArrayList<>(roles);
    }
}