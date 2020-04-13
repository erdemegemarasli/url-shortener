package bilshort.user.services;

import bilshort.user.models.Business;
import bilshort.user.models.Role;
import bilshort.user.models.User;
import bilshort.user.repositories.BusinessRepository;
import bilshort.user.repositories.RoleRepository;
import bilshort.user.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserDetailsServiceEx implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);

        if (user == null)
            return null;

        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), true, true, true, true, authorities);
    }

    public User save(User user) {
        Role userRole = roleRepository.findByRole("USER");
        Business business = businessRepository.findByApiKey("Anonymous");

        user.setPassword(passwordEncoder.encode(user.getPassword()))
                .setRoles(new HashSet<>(Collections.singletonList(userRole)))
                .setBusinessId(business);

        return userRepository.save(user);
    }

    public User save(User user, String apiKey) {
        Role userRole = roleRepository.findByRole("USER");
        Business business = businessRepository.findByApiKey(apiKey);

        user.setPassword(passwordEncoder.encode(user.getPassword()))
                .setRoles(new HashSet<>(Collections.singletonList(userRole)))
                .setBusinessId(business);

        return userRepository.save(user);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new ArrayList<>(roles);
    }
}