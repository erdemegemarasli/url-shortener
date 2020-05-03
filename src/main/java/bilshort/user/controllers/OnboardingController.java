package bilshort.user.controllers;

import bilshort.security.JwtTokenUtil;
import bilshort.user.models.AuthDTO;
import bilshort.user.models.User;
import bilshort.user.models.UserDTO;
import bilshort.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RequestMapping("api/v1")
@RestController
public class OnboardingController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthDTO authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUserName());

        User user = userService.getUserByUserName(userDetails.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        AuthDTO authDTO = new AuthDTO(token);
        authDTO.setUserId(user.getUserId());
        authDTO.setUserName(user.getUserName());
        Boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN"));
        authDTO.setIsAdmin(isAdmin);

        return ResponseEntity.ok(authDTO);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> createNewUser(@RequestBody AuthDTO authDTO) {
        authDTO.setUserId(null);

        if (authDTO.getUserName() == null)
        {
            return ResponseEntity.badRequest().body("UserName can not be empty.");
        }
        if (authDTO.getEmail() == null)
        {
            return ResponseEntity.badRequest().body("Email can not be empty.");
        }
        if (authDTO.getPassword() == null)
        {
            return ResponseEntity.badRequest().body("Password can not be empty.");
        }

        if (!isValidEmail(authDTO.getEmail())){
            return ResponseEntity.badRequest().body("Please enter a valid email.");
        }

        if (authDTO.getPassword().length() > 32 || authDTO.getPassword().length() < 8){
            return ResponseEntity.badRequest().body("Password length must be between 8 and 32.");
        }
        if (authDTO.getUserName().length() > 16 || authDTO.getUserName().length() < 5){
            return ResponseEntity.badRequest().body("Username length must be between 5 and 16.");
        }
        if (authDTO.getEmail().length() > 64 || authDTO.getEmail().length() < 5){
            return ResponseEntity.badRequest().body("Email length must be between 5 and 64.");
        }

        UserDetails existingUser = userService.loadUserByUsername(authDTO.getUserName());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("There is already a user registered with the username provided");
        }

        if (userService.loadUserByEmail(authDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().body("This email already registered.");
        }


        User user = new User().setUserName(authDTO.getUserName())
                .setEmail(authDTO.getEmail())
                .setPassword(authDTO.getPassword());

        user = userService.save(user, true);

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

    @PostMapping("apiKey")
    public ResponseEntity<?> generateApiKey(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isUser = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin && !isUser) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        String userName = authentication.getName();
        User user = userService.getUserByUserName(userName);

        if (user == null){
            return ResponseEntity.notFound().build();
        }

        String apiKey = user.getApiKey();

        if (apiKey != null){
            return ResponseEntity.badRequest().body("You already have an API Key:" + apiKey);
        }

        apiKey = generateRandomApiKey();
        user.setApiKey(apiKey);
        userService.save(user);

        return ResponseEntity.ok(apiKey);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
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
