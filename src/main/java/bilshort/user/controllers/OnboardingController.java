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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthDTO(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> createNewUser(@RequestBody AuthDTO authDTO) {
        UserDetails existingUser = userService.loadUserByUsername(authDTO.getUserName());

        if (existingUser != null) {
            return ResponseEntity.badRequest().body("There is already a user registered with the username provided");
        }

        User user = new User().setUserName(authDTO.getUserName())
                .setEmail(authDTO.getEmail())
                .setPassword(authDTO.getPassword());

        user = userService.save(user);

        UserDTO response = new UserDTO();
        response.setAdmin(user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN")));
        response.setUserName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setApiKey(user.getApiKey());
        response.setTotalRightsUsed(user.getTotalRightsUsed());
        response.setMaxRightsAvailable(user.getMaxRightsAvailable());

        return ResponseEntity.ok(response);
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
}
