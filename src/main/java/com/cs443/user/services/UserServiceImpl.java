package com.cs443.user.services;

import com.cs443.user.models.Business;
import com.cs443.user.models.Role;
import com.cs443.user.models.User;
import com.cs443.user.repositories.BusinessRepository;
import com.cs443.user.repositories.RoleRepository;
import com.cs443.user.repositories.UserRepository;
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