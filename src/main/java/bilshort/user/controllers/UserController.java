package bilshort.user.controllers;

import bilshort.user.models.JwtRequest;
import bilshort.user.models.JwtResponse;
import bilshort.user.models.User;
import bilshort.user.security.JwtTokenUtil;
import bilshort.user.services.BilshortUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private BilshortUserDetailsService bilshortUserDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = bilshortUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/register/{API_KEY}")
    public ResponseEntity<String> createNewUserWithAPI(@RequestBody User user, @PathVariable("API_KEY") String api_key) {
        UserDetails existingUser = bilshortUserDetailsService.loadUserByUsername(user.getUserName());

        String message = "User created";
        HttpStatus httpStatus = HttpStatus.CREATED;

        if (existingUser != null) {
            message = "There is already a user registered with the user name provided";
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else {
            bilshortUserDetailsService.save(user, api_key);
        }

        return new ResponseEntity<>(message, httpStatus);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> createNewUser(@RequestBody User user) {
        UserDetails existingUser = bilshortUserDetailsService.loadUserByUsername(user.getUserName());

        String message = "User created";
        HttpStatus httpStatus = HttpStatus.CREATED;

        if (existingUser != null) {
            message = "There is already a user registered with the user name provided";
        }
        else {
            bilshortUserDetailsService.save(user);
        }

        return new ResponseEntity<>(message, httpStatus);
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