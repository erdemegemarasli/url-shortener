package bilshort.user.controllers;

import bilshort.user.models.User;
import bilshort.user.models.UserDTO;
import bilshort.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("api/v1/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @NonNull UserDTO userDTO) {

        if (userService.loadUserByUsername(userDTO.getUserName()) != null) {
            return ResponseEntity.badRequest().body("Already existing user!");
        }

        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        return ResponseEntity.ok(userService.save(user, userDTO.getAdmin()));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam Map<String, String> params) {
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        List<UserDTO> users = new ArrayList<>();

        if (params.isEmpty()) {
            for (User user : userService.getAllUsers()) {
                UserDTO tempUser = new UserDTO();

                tempUser.setUserName(user.getUserName());
                tempUser.setAdmin(user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN")));
                tempUser.setEmail(user.getEmail());
                tempUser.setMaxRightsAvailable(user.getMaxRightsAvailable());
                tempUser.setTotalRightsUsed(user.getTotalRightsUsed());
                tempUser.setApiKey(user.getApiKey());

                users.add(tempUser);
            }
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Integer id) {




        return ResponseEntity.ok(null);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {

        return ResponseEntity.ok(null);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") int id, @RequestBody @NonNull UserDTO userDTO) {

        return ResponseEntity.ok(null);
    }
}