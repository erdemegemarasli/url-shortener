package bilshort.user.controllers;

import bilshort.user.models.JwtRequest;
import bilshort.user.models.JwtResponse;
import bilshort.user.models.User;
import bilshort.user.security.JwtTokenUtil;
import bilshort.user.services.UserDetailsServiceEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1")
@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceEx bilshortUserDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = bilshortUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> createNewUser(@RequestBody JwtRequest registrationRequest) {
        UserDetails existingUser = bilshortUserDetailsService.loadUserByUsername(registrationRequest.getUsername());

        if (existingUser != null) {
            return ResponseEntity.badRequest().body("There is already a user registered with the username provided");
        }

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());

        String apiKey = registrationRequest.getApiKey();

        if (apiKey == null) {
            return ResponseEntity.ok(bilshortUserDetailsService.save(user));
        }
        else {
            return ResponseEntity.ok(bilshortUserDetailsService.save(user, apiKey));
        }
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            System.out.println("User is disabled.");
            //throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("User credentials are wrong.");
            //throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}