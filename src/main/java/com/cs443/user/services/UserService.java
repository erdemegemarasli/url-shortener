package com.cs443.user.services;

import com.cs443.user.models.User;

public interface UserService {
    User findUserByUserName(String userName);
    User saveUser(User user);
    User saveUser(User user, String apiKey);
}