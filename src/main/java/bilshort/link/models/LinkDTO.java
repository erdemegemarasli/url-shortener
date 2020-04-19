package bilshort.link.models;

import java.io.Serializable;

public class LinkDTO implements Serializable {

    // https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
    private static final long serialVersionUID = 3385324889163774845L;

    private String longUrl;
    private String expTime;
    private String shortUrl;
    private String description;
    private Integer id;
    private String username;


    public LinkDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
