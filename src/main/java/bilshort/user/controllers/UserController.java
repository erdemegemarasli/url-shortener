package bilshort.user.controllers;

import bilshort.user.models.User;
import bilshort.user.models.UserDTO;
import bilshort.user.services.RoleService;
import bilshort.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("api/v1/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @NonNull UserDTO userDTO) {

        userDTO.setUserId(null);
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        if (userDTO.getUserName() == null)
        {
            return ResponseEntity.badRequest().body("UserName can not be empty.");
        }
        if (userDTO.getEmail() == null)
        {
            return ResponseEntity.badRequest().body("Email can not be empty.");
        }
        if (userDTO.getPassword() == null)
        {
            return ResponseEntity.badRequest().body("Password can not be empty.");
        }

        if (userDTO.getPassword().length() > 32 || userDTO.getPassword().length() < 8){
            return ResponseEntity.badRequest().body("Password length must be between 8 and 32.");
        }
        if (userDTO.getUserName().length() > 16 || userDTO.getUserName().length() < 5){
            return ResponseEntity.badRequest().body("Username length must be between 5 and 16.");
        }
        if (userDTO.getEmail().length() > 64 || userDTO.getEmail().length() < 5){
            return ResponseEntity.badRequest().body("Email length must be between 5 and 64.");
        }

        if (!isValidEmail(userDTO.getEmail())){
            return ResponseEntity.badRequest().body("Please enter a valid email.");
        }


        if (userService.loadUserByUsername(userDTO.getUserName()) != null) {
            return ResponseEntity.badRequest().body("There is already a user registered with the username provided");
        }
        if (userService.loadUserByEmail(userDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().body("This email already registered.");
        }


        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        user = userService.save(user, false, true);

        UserDTO response = new UserDTO();
        response.setUserId(user.getUserId());
        response.setIsAdmin(user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN")));
        response.setUserName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setApiKey(user.getApiKey());
        response.setTotalRightsUsed(user.getTotalRightsUsed());
        response.setMaxRightsAvailable(user.getMaxRightsAvailable());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        List<UserDTO> users = new ArrayList<>();

        for (User user : userService.getAllUsers()) {
            UserDTO tempUser = new UserDTO();

            tempUser.setUserId(user.getUserId());
            tempUser.setUserName(user.getUserName());
            tempUser.setIsAdmin(user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN")));
            tempUser.setEmail(user.getEmail());
            tempUser.setMaxRightsAvailable(user.getMaxRightsAvailable());
            tempUser.setTotalRightsUsed(user.getTotalRightsUsed());
            tempUser.setApiKey(user.getApiKey());

            users.add(tempUser);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Integer id) {
        boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin && !isUser) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (!user.getUserName().equals(SecurityContextHolder.getContext().getAuthentication().getName()) && !isAdmin){
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setIsAdmin(user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN")));
        userDTO.setEmail(user.getEmail());
        userDTO.setMaxRightsAvailable(user.getMaxRightsAvailable());
        userDTO.setTotalRightsUsed(user.getTotalRightsUsed());
        userDTO.setApiKey(user.getApiKey());

        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {

        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        Long operationCode = userService.deleteUserById(id);

        if (operationCode == 0) {
            return ResponseEntity.notFound().build();
        }
        else if (operationCode == 1) {
            return ResponseEntity.ok().body("Deletion Successful");
        }

        return ResponseEntity.badRequest().body("Unexpected Error");
    }

    @PutMapping(path = "{id}/apiKey")
    public ResponseEntity<?> changeApiKey(@PathVariable("id") int id){

        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        User user = userService.getUserById(id);

        if (user == null){
            return ResponseEntity.badRequest().body("No such user found with id " + id + ".");
        }

        String apiKey = generateRandomApiKey();

        if (apiKey == null){
            return ResponseEntity.badRequest().body("An unexpected error occurred, try again.");
        }

        user.setApiKey(apiKey);
        user.setMaxRightsAvailable(60);
        userService.save(user);

        return ResponseEntity.ok(apiKey);
    }


    @PutMapping(path = "{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") int id, @RequestBody @NonNull UserDTO userDTO) {

        userDTO.setUserId(null);
        boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin && !isUser) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        User user = userService.getUserById(id);
        Boolean isPasswordChanged = false;

        if (user == null) {
            return ResponseEntity.notFound().build();
        }


        if (!user.getUserName().equals(SecurityContextHolder.getContext().getAuthentication().getName()) && !isAdmin){
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        if (isAdmin){

            if (userDTO.getApiKey() != null){
                user.setApiKey(userDTO.getApiKey());
            }

            if (userDTO.getTotalRightsUsed() != null){
                user.setTotalRightsUsed(userDTO.getTotalRightsUsed());
            }

            if (userDTO.getMaxRightsAvailable() != null){
                user.setMaxRightsAvailable(userDTO.getMaxRightsAvailable());
            }
        }

        if (userDTO.getEmail() != null){

            if (userDTO.getEmail().length() > 64 || userDTO.getEmail().length() < 5){
                return ResponseEntity.badRequest().body("Email length must be between 5 and 64.");
            }

            if (!isValidEmail(userDTO.getEmail())){
                return ResponseEntity.badRequest().body("Please enter a valid email.");
            }

            if (userService.loadUserByEmail(userDTO.getEmail()) != null) {
                return ResponseEntity.badRequest().body("This email already registered.");
            }

            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getUserName() != null){

            if (userDTO.getUserName().length() > 16 || userDTO.getUserName().length() < 5){
                return ResponseEntity.badRequest().body("Username length must be between 5 and 16.");
            }

            if (userService.loadUserByUsername(userDTO.getUserName()) != null) {
                return ResponseEntity.badRequest().body("There is already a user registered with the username provided");
            }
            user.setUserName(userDTO.getUserName());
        }

        if (userDTO.getPassword() != null){
            isPasswordChanged = true;

            if (userDTO.getPassword().length() > 32 || userDTO.getPassword().length() < 8){
                return ResponseEntity.badRequest().body("Password length must be between 8 and 32.");
            }
            user.setPassword(userDTO.getPassword());
        }



        if (userDTO.getIsAdmin() != null){
            if (isAdmin){
                user = userService.save(user, userDTO.getIsAdmin(), isPasswordChanged);
            }
            else{
                return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
            }
        }
        else{
            user = userService.save(user, isPasswordChanged);
        }


        UserDTO response = new UserDTO();
        response.setUserId(user.getUserId());
        response.setIsAdmin(user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN")));
        response.setUserName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setApiKey(user.getApiKey());
        response.setTotalRightsUsed(user.getTotalRightsUsed());
        response.setMaxRightsAvailable(user.getMaxRightsAvailable());

        return ResponseEntity.ok(response);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private String generateRandomApiKey() {
        for (int i = 0; i < 10; i++) {
            String randomCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);

            if (userService.loadUserByApiKey(randomCode) == null) {
                return randomCode;
            }
        }

        return null;
    }
}