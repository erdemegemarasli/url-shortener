package bilshort.user.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 7838351376229501972L;

    private String userName;
    private String password;
    private String email;
    private Boolean isAdmin;
    private Integer totalRightsUsed;
    private Integer maxRightsAvailable;
    private String apiKey;
    private Integer userId;

    public UserDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Integer getTotalRightsUsed() {
        return totalRightsUsed;
    }

    public void setTotalRightsUsed(Integer totalRightsUsed) {
        this.totalRightsUsed = totalRightsUsed;
    }

    public Integer getMaxRightsAvailable() {
        return maxRightsAvailable;
    }

    public void setMaxRightsAvailable(Integer maxRightsAvailable) {
        this.maxRightsAvailable = maxRightsAvailable;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
