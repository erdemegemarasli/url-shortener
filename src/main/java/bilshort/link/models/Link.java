package bilshort.link.models;

import bilshort.user.models.User;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "link_id")
    private Integer linkId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User userId;

    @Column(name = "short_link")
    private String shortLink;

    @Column(name = "long_link")
    private String longLink;

    @Column(name = "time_to_live")
    private String timeToLive;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "visit_count", columnDefinition = "int default 0")
    private int visitCount;

    public Link() {
    }

    public Link(User userId, String shortLink, String longLink, String timeToLive, String createDate, int visitCount) {
        this.userId = userId;
        this.shortLink = shortLink;
        this.longLink = longLink;
        this.timeToLive = timeToLive;
        this.createDate = createDate;
        this.visitCount = visitCount;
    }

    public Integer getLinkId() {
        return linkId;
    }

    public Link setLinkId(Integer linkId) {
        this.linkId = linkId;
        return this;
    }

    public String getShortLink() {
        return shortLink;
    }

    public Link setShortLink(String shortLink) {
        this.shortLink = shortLink;
        return this;
    }

    public String getLongLink() {
        return longLink;
    }

    public Link setLongLink(String longLink) {
        this.longLink = longLink;
        return this;
    }

    public String getTimeToLive() {
        return timeToLive;
    }

    public Link setTimeToLive(String timeToLive) {
        this.timeToLive = timeToLive;
        return this;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public Link setVisitCount(int visitCount) {
        this.visitCount = visitCount;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Link setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public User getUserId() {
        return userId;
    }

    public Link setUserId(User userId) {
        this.userId = userId;
        return this;
    }
}
