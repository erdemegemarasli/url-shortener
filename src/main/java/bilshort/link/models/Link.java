package bilshort.link.models;

import bilshort.user.models.User;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "link_id")
    private Integer linkId;

    @ManyToOne
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;

    @Column(name = "code")
    private String code;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "exp_time")
    private String expTime;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "visit_count", columnDefinition = "int default 0")
    private int visitCount;

    public Link() {
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }
}