package bilshort.user.models;


import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    @Length(min = 5, max = 15)
    private String username;

    @ManyToOne
    @JoinColumn(name="business_id", nullable=false)
    private Business businessId;

    @Column(name = "password")
    @Length(min = 8)
    private String password;

    @Column(name = "total_links_visited", columnDefinition = "int default 0")
    private int totalLinks;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
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

    public int getTotalLinks() {
        return totalLinks;
    }

    public User setTotalLinks(int totalLinks) {
        this.totalLinks = totalLinks;
        return this;
    }

    public Business getBusinessId() {
        return businessId;
    }

    public User setBusinessId(Business businessId) {
        this.businessId = businessId;
        return this;
    }
}
