package bilshort.user.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "user_name")
    @Length(min = 5, max = 16)
    private String userName;

    @NotNull
    @Column(name = "email", unique = true)
    @Length(min = 5, max = 64)
    private String email;

    @NotNull
    @Column(name = "password")
    @Length(min = 8)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "total_rights_used", columnDefinition = "int default 0")
    private int totalRightsUsed;

    @Column(name = "max_rights_available", columnDefinition = "int default 10")
    private int maxRightsAvailable;

    public Integer getUserId() {
        return userId;
    }

    public User setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getTotalRightsUsed() {
        return totalRightsUsed;
    }

    public void setTotalRightsUsed(int totalRightsUsed) {
        this.totalRightsUsed = totalRightsUsed;
    }

    public int getMaxRightsAvailable() {
        return maxRightsAvailable;
    }

    public void setMaxRightsAvailable(int maxRightsAvailable) {
        this.maxRightsAvailable = maxRightsAvailable;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
