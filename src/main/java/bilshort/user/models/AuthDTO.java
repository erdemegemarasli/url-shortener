package bilshort.user.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDTO implements Serializable {

    // https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
    private static final long serialVersionUID = 7048489224107438116L;

    private String userName;
    private String email;
    private String password;
    private String token;
    private Integer userId;
    private Boolean isAdmin;

    public AuthDTO() {
    }

    public AuthDTO(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public AuthDTO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AuthDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getToken() {
        return token;
    }

    public AuthDTO setToken(String token) {
        this.token = token;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public AuthDTO setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public AuthDTO setIsAdmin(Boolean admin) {
        this.isAdmin = admin;
        return this;
    }
}