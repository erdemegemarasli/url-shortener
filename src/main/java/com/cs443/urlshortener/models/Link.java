package com.cs443.urlshortener.models;

import com.cs443.user.models.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "link_id")
    private Integer id;

    @ManyToOne
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

    public Integer getId() {
        return id;
    }

    public Link setId(Integer id) {
        this.id = id;
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
