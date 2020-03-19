package com.cs443.urlshortener.models;

import java.util.Date;

public class Link {
    private String shortLink;
    private String longLink;
    private Date timeToLive;

    public Link() {
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

    public Date getTimeToLive() {
        return timeToLive;
    }

    public Link setTimeToLive(Date timeToLive) {
        this.timeToLive = timeToLive;
        return this;
    }
}
