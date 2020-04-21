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
    private long expTime;

    @Column(name = "created_at")
    private long createdAt;

    @Column(name = "visited_count_from_chrome", columnDefinition = "int default 0")
    private int visitCountFromChrome;

    @Column(name = "visited_count_from_firefox", columnDefinition = "int default 0")
    private int visitCountFromFirefox;

    @Column(name = "visited_count_from_safari", columnDefinition = "int default 0")
    private int visitCountFromSafari;

    @Column(name = "visited_count_from_ie", columnDefinition = "int default 0")
    private int visitCountFromIE;

    @Column(name = "visited_count_from_other_browser", columnDefinition = "int default 0")
    private int visitCountFromOtherBrowser;

    @Column(name = "visited_count_from_windows", columnDefinition = "int default 0")
    private int visitCountFromWindows;

    @Column(name = "visited_count_from_osx", columnDefinition = "int default 0")
    private int visitCountFromOsx;

    @Column(name = "visited_count_from_linux", columnDefinition = "int default 0")
    private int visitCountFromLinux;

    @Column(name = "visited_count_from_ios", columnDefinition = "int default 0")
    private int visitCountFromIOS;

    @Column(name = "visited_count_from_android", columnDefinition = "int default 0")
    private int visitCountFromAndroid;

    @Column(name = "visited_count_from_other_os", columnDefinition = "int default 0")
    private int visitCountFromOtherOs;

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

    public long getExpTime() {
        return expTime;
    }

    public void setExpTime(long expTime) {
        this.expTime = expTime;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getVisitCountFromChrome() {
        return visitCountFromChrome;
    }

    public void setVisitCountFromChrome(int visitCountFromChrome) {
        this.visitCountFromChrome = visitCountFromChrome;
    }

    public int getVisitCountFromFirefox() {
        return visitCountFromFirefox;
    }

    public void setVisitCountFromFirefox(int visitCountFromFirefox) {
        this.visitCountFromFirefox = visitCountFromFirefox;
    }

    public int getVisitCountFromSafari() {
        return visitCountFromSafari;
    }

    public void setVisitCountFromSafari(int visitCountFromSafari) {
        this.visitCountFromSafari = visitCountFromSafari;
    }

    public int getVisitCountFromIE() {
        return visitCountFromIE;
    }

    public void setVisitCountFromIE(int visitCountFromIE) {
        this.visitCountFromIE = visitCountFromIE;
    }

    public int getVisitCountFromOtherBrowser() {
        return visitCountFromOtherBrowser;
    }

    public void setVisitCountFromOtherBrowser(int visitCountFromOtherBrowser) {
        this.visitCountFromOtherBrowser = visitCountFromOtherBrowser;
    }

    public int getVisitCountFromWindows() {
        return visitCountFromWindows;
    }

    public void setVisitCountFromWindows(int visitCountFromWindows) {
        this.visitCountFromWindows = visitCountFromWindows;
    }

    public int getVisitCountFromOsx() {
        return visitCountFromOsx;
    }

    public void setVisitCountFromOsx(int visitCountFromOsx) {
        this.visitCountFromOsx = visitCountFromOsx;
    }

    public int getVisitCountFromLinux() {
        return visitCountFromLinux;
    }

    public void setVisitCountFromLinux(int visitCountFromLinux) {
        this.visitCountFromLinux = visitCountFromLinux;
    }

    public int getVisitCountFromIOS() {
        return visitCountFromIOS;
    }

    public void setVisitCountFromIOS(int visitCountFromIOS) {
        this.visitCountFromIOS = visitCountFromIOS;
    }

    public int getVisitCountFromAndroid() {
        return visitCountFromAndroid;
    }

    public void setVisitCountFromAndroid(int visitCountFromAndroid) {
        this.visitCountFromAndroid = visitCountFromAndroid;
    }

    public int getVisitCountFromOtherOs() {
        return visitCountFromOtherOs;
    }

    public void setVisitCountFromOtherOs(int visitCountFromOtherOs) {
        this.visitCountFromOtherOs = visitCountFromOtherOs;
    }
}