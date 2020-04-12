package bilshort.user.services;

import bilshort.user.models.User;

public interface UserService {
    User findUserByUserName(String userName);
    User saveUser(User user);
    User saveUser(User user, String apiKey);
}