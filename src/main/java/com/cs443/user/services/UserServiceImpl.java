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
    public User saveUser(User user, String apiKey) {
        Role userRole = roleRepository.findByRole("USER");
        Business business = businessRepository.findByApiKey(apiKey);
        boolean isB2C = false;

        // todo: su an business_id foreign key oldugu icin null olamiyor. o yuzden default bir sirketi bulup ona atiyorum b2c oldugu zaman. bu design'i guzellestirebiliriz.
        if (business == null){
            business = businessRepository.findById(1).get();
            isB2C = true;
        }

        user
                .setPassword(bCryptPasswordEncoder.encode(user.getPassword()))
                .setRoles(new HashSet<>(Collections.singletonList(userRole)))
                .setB2C(isB2C)
                .setBusinessId(business);

        return userRepository.save(user);
    }
}
