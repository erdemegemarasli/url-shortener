package bilshort.user.controllers;

import bilshort.user.models.User;
import bilshort.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register/{API_KEY}")
    public ResponseEntity<String> createNewUserWithAPI(@RequestBody User user, @PathVariable("API_KEY") String api_key) {
        User existingUser = userService.findUserByUserName(user.getUserName());
        String message = "User created";
        HttpStatus httpStatus = HttpStatus.CREATED;

        if (existingUser != null) {
            message = "There is already a user registered with the user name provided";
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else {
            userService.saveUser(user, api_key);
        }

        return new ResponseEntity<>(message, httpStatus);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> createNewUser(@RequestBody User user) {
        User existingUser = userService.findUserByUserName(user.getUserName());
        String message = "User created";
        HttpStatus httpStatus = HttpStatus.CREATED;

        if (existingUser != null) {
            message = "There is already a user registered with the user name provided";
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else {
            userService.saveUser(user);
        }

        return new ResponseEntity<>(message, httpStatus);
    }

    @GetMapping(value="/admin")
    public ResponseEntity<User> admin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value="/user")
    public ResponseEntity<User> user(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}