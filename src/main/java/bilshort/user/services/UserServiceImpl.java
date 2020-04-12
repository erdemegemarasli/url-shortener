package bilshort.user.services;

import bilshort.user.models.Business;
import bilshort.user.models.Role;
import bilshort.user.models.User;
import bilshort.user.repositories.BusinessRepository;
import bilshort.user.repositories.RoleRepository;
import bilshort.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User saveUser(User user) {
        Role userRole = roleRepository.findByRole("USER");
        Business business = businessRepository.findByApiKey("Anonymous");

        user
                .setPassword(bCryptPasswordEncoder.encode(user.getPassword()))
                .setRoles(new HashSet<>(Collections.singletonList(userRole)))
                .setBusinessId(business);

        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user, String apiKey) {
        Role userRole = roleRepository.findByRole("USER");
        Business business = businessRepository.findByApiKey(apiKey);

        if (business == null) {
            business = businessRepository.findByApiKey("Anonymous");
        }

        user
                .setPassword(bCryptPasswordEncoder.encode(user.getPassword()))
                .setRoles(new HashSet<>(Collections.singletonList(userRole)))
                .setBusinessId(business);

        return userRepository.save(user);
    }
}