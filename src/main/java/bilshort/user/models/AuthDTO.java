package bilshort.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class AuthDTO implements Serializable {

    // https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
    private static final long serialVersionUID = 7048489224107438116L;

    private String username;
    private String password;
    private String token;

    public AuthDTO() {
    }

    public AuthDTO(String token) {
        this.token = token;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}