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
    private Long expTime;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "visited_count_from_chrome", columnDefinition = "int default 0")
    private Integer visitCountFromChrome;

    @Column(name = "visited_count_from_firefox", columnDefinition = "int default 0")
    private Integer visitCountFromFirefox;

    @Column(name = "visited_count_from_safari", columnDefinition = "int default 0")
    private Integer visitCountFromSafari;

    @Column(name = "visited_count_from_ie", columnDefinition = "int default 0")
    private Integer visitCountFromIE;

    @Column(name = "visited_count_from_other_browser", columnDefinition = "int default 0")
    private Integer visitCountFromOtherBrowser;

    @Column(name = "visited_count_from_windows", columnDefinition = "int default 0")
    private Integer visitCountFromWindows;

    @Column(name = "visited_count_from_osx", columnDefinition = "int default 0")
    private Integer visitCountFromOsx;

    @Column(name = "visited_count_from_linux", columnDefinition = "int default 0")
    private Integer visitCountFromLinux;

    @Column(name = "visited_count_from_ios", columnDefinition = "int default 0")
    private Integer visitCountFromIOS;

    @Column(name = "visited_count_from_android", columnDefinition = "int default 0")
    private Integer visitCountFromAndroid;

    @Column(name = "visited_count_from_other_os", columnDefinition = "int default 0")
    private Integer visitCountFromOtherOs;

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

    public Long getExpTime() {
        return expTime;
    }

    public void setExpTime(Long expTime) {
        this.expTime = expTime;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getVisitCountFromChrome() {
        return visitCountFromChrome;
    }

    public void setVisitCountFromChrome(Integer visitCountFromChrome) {
        this.visitCountFromChrome = visitCountFromChrome;
    }

    public Integer getVisitCountFromFirefox() {
        return visitCountFromFirefox;
    }

    public void setVisitCountFromFirefox(Integer visitCountFromFirefox) {
        this.visitCountFromFirefox = visitCountFromFirefox;
    }

    public Integer getVisitCountFromSafari() {
        return visitCountFromSafari;
    }

    public void setVisitCountFromSafari(Integer visitCountFromSafari) {
        this.visitCountFromSafari = visitCountFromSafari;
    }

    public Integer getVisitCountFromIE() {
        return visitCountFromIE;
    }

    public void setVisitCountFromIE(Integer visitCountFromIE) {
        this.visitCountFromIE = visitCountFromIE;
    }

    public Integer getVisitCountFromOtherBrowser() {
        return visitCountFromOtherBrowser;
    }

    public void setVisitCountFromOtherBrowser(Integer visitCountFromOtherBrowser) {
        this.visitCountFromOtherBrowser = visitCountFromOtherBrowser;
    }

    public Integer getVisitCountFromWindows() {
        return visitCountFromWindows;
    }

    public void setVisitCountFromWindows(Integer visitCountFromWindows) {
        this.visitCountFromWindows = visitCountFromWindows;
    }

    public Integer getVisitCountFromOsx() {
        return visitCountFromOsx;
    }

    public void setVisitCountFromOsx(Integer visitCountFromOsx) {
        this.visitCountFromOsx = visitCountFromOsx;
    }

    public Integer getVisitCountFromLinux() {
        return visitCountFromLinux;
    }

    public void setVisitCountFromLinux(Integer visitCountFromLinux) {
        this.visitCountFromLinux = visitCountFromLinux;
    }

    public Integer getVisitCountFromIOS() {
        return visitCountFromIOS;
    }

    public void setVisitCountFromIOS(Integer visitCountFromIOS) {
        this.visitCountFromIOS = visitCountFromIOS;
    }

    public Integer getVisitCountFromAndroid() {
        return visitCountFromAndroid;
    }

    public void setVisitCountFromAndroid(Integer visitCountFromAndroid) {
        this.visitCountFromAndroid = visitCountFromAndroid;
    }

    public Integer getVisitCountFromOtherOs() {
        return visitCountFromOtherOs;
    }

    public void setVisitCountFromOtherOs(Integer visitCountFromOtherOs) {
        this.visitCountFromOtherOs = visitCountFromOtherOs;
    }
}