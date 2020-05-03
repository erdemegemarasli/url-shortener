package bilshort.link.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinkDTO implements Serializable {

    // https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
    private static final long serialVersionUID = 3385324889163774845L;

    private String url;
    private Long expTime;
    private String code;
    private String description;
    private Integer linkId;
    private String userName;
    private Integer totalVisitedCount;
    private Integer visitedCountFromChrome;
    private Integer visitedCountFromFirefox;
    private Integer visitedCountFromSafari;
    private Integer visitedCountFromIe;
    private Integer visitedCountFromOtherBrowser;
    private Integer visitedCountFromWindows;
    private Integer visitedCountFromOsx;
    private Integer visitedCountFromLinux;
    private Integer visitedCountFromIOS;
    private Integer visitedCountFromAndroid;
    private Integer visitedCountFromOtherOs;
    private Integer visitedCountFromComputer;
    private Integer visitedCountFromMobile;
    private Long createdAt;



    public LinkDTO() {
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getExpTime() {
        return expTime;
    }

    public void setExpTime(Long expTime) {
        this.expTime = expTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalVisitedCount() {
        return totalVisitedCount;
    }

    public void setTotalVisitedCount(Integer totalVisitedCount) {
        this.totalVisitedCount = totalVisitedCount;
    }

    public Integer getVisitedCountFromChrome() {
        return visitedCountFromChrome;
    }

    public void setVisitedCountFromChrome(Integer visitedCountFromChrome) {
        this.visitedCountFromChrome = visitedCountFromChrome;
    }

    public Integer getVisitedCountFromFirefox() {
        return visitedCountFromFirefox;
    }

    public void setVisitedCountFromFirefox(Integer visitedCountFromFirefox) {
        this.visitedCountFromFirefox = visitedCountFromFirefox;
    }

    public Integer getVisitedCountFromSafari() {
        return visitedCountFromSafari;
    }

    public void setVisitedCountFromSafari(Integer visitedCountFromSafari) {
        this.visitedCountFromSafari = visitedCountFromSafari;
    }

    public Integer getVisitedCountFromIe() {
        return visitedCountFromIe;
    }

    public void setVisitedCountFromIe(Integer visitedCountFromIe) {
        this.visitedCountFromIe = visitedCountFromIe;
    }

    public Integer getVisitedCountFromOtherBrowser() {
        return visitedCountFromOtherBrowser;
    }

    public void setVisitedCountFromOtherBrowser(Integer visitedCountFromOtherBrowser) {
        this.visitedCountFromOtherBrowser = visitedCountFromOtherBrowser;
    }

    public Integer getVisitedCountFromWindows() {
        return visitedCountFromWindows;
    }

    public void setVisitedCountFromWindows(Integer visitedCountFromWindows) {
        this.visitedCountFromWindows = visitedCountFromWindows;
    }

    public Integer getVisitedCountFromOsx() {
        return visitedCountFromOsx;
    }

    public void setVisitedCountFromOsx(Integer visitedCountFromOsx) {
        this.visitedCountFromOsx = visitedCountFromOsx;
    }

    public Integer getVisitedCountFromLinux() {
        return visitedCountFromLinux;
    }

    public void setVisitedCountFromLinux(Integer visitedCountFromLinux) {
        this.visitedCountFromLinux = visitedCountFromLinux;
    }

    public Integer getVisitedCountFromIOS() {
        return visitedCountFromIOS;
    }

    public void setVisitedCountFromIOS(Integer visitedCountFromIOS) {
        this.visitedCountFromIOS = visitedCountFromIOS;
    }

    public Integer getVisitedCountFromAndroid() {
        return visitedCountFromAndroid;
    }

    public void setVisitedCountFromAndroid(Integer visitedCountFromAndroid) {
        this.visitedCountFromAndroid = visitedCountFromAndroid;
    }

    public Integer getVisitedCountFromOtherOs() {
        return visitedCountFromOtherOs;
    }

    public void setVisitedCountFromOtherOs(Integer visitedCountFromOtherOs) {
        this.visitedCountFromOtherOs = visitedCountFromOtherOs;
    }

    public Integer getVisitedCountFromComputer() {
        return visitedCountFromComputer;
    }

    public void setVisitedCountFromComputer(Integer visitedCountFromComputer) {
        this.visitedCountFromComputer = visitedCountFromComputer;
    }

    public Integer getVisitedCountFromMobile() {
        return visitedCountFromMobile;
    }

    public void setVisitedCountFromMobile(Integer visitedCountFromMobile) {
        this.visitedCountFromMobile = visitedCountFromMobile;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
