package bilshort.user.models;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    // https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
    private static final long serialVersionUID = 7048489224107438116L;

    private String username;
    private String password;
    private String apiKey;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password, String apiKey) {
        this.setUsername(username);
        this.setPassword(password);
        this.setApiKey(apiKey);
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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}