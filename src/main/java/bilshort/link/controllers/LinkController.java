package bilshort.link.controllers;

import bilshort.link.models.Link;
import bilshort.link.models.LinkDTO;
import bilshort.link.services.LinkService;
import bilshort.user.models.User;
import bilshort.user.services.UserService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("api/v1/link")
@RestController
public class LinkController {

    @Value("${domain.host}")
    private String domain;

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserService userService;

/*
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    boolean isUser  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
    boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));
*/

    @PostMapping
    public ResponseEntity<?> createShortLink(@RequestBody @NonNull LinkDTO linkDTO) {
        UrlValidator urlValidator = new UrlValidator();

        if (linkDTO.getUrl() == null){
            return ResponseEntity.badRequest().body("URL cannot be empty.");
        }

        String url = linkDTO.getUrl();

        if (url.length() < 8) {
            url = "http://" + url;
        }
        else if (!url.substring(0, 8).equals("https://") && !url.substring(0, 7).equals("http://")) {
            url = "http://" + url;
        }

        linkDTO.setUrl(url);

        if (!urlValidator.isValid(linkDTO.getUrl())) {
            return ResponseEntity.badRequest().body("Given URL is invalid!");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(username);

        if (user.getTotalRightsUsed() >= user.getMaxRightsAvailable()){
            return ResponseEntity.badRequest().body("Maximum available links is reached, delete some before creating new ones!");
        }

        if (linkDTO.getCode() == null) { // Random
            String randomCode = generateRandomCode();

            if (randomCode == null) {
                return ResponseEntity.badRequest().body("Unexpected Error!");
            }
            else {
                linkDTO.setCode(randomCode);
            }
        }
        else { // Custom
            if (linkService.getLinkByCode(linkDTO.getCode()) == null) {
                if (linkDTO.getCode().length() == 0){
                    return ResponseEntity.badRequest().body("Code cannot be empty.");
                }
                linkDTO.setCode(linkDTO.getCode());
            }
            else {
                return ResponseEntity.badRequest().body("Custom URL already exists!");
            }
        }

        linkDTO.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        Link link = linkService.createShortLink(linkDTO);

        user.setTotalRightsUsed(user.getTotalRightsUsed() + 1);
        userService.save(user);

        if (link == null){
            return ResponseEntity.badRequest().body("Wrong Input Format");
        }

        LinkDTO response = new LinkDTO();
        response.setExpTime(link.getExpTime());
        response.setUrl(link.getUrl());
        response.setCode(domain + link.getCode());
        response.setLinkId(link.getLinkId());
        response.setDescription(link.getDescription());

        Integer totalVisitCount = link.getVisitCountFromChrome() + link.getVisitCountFromFirefox()
                + link.getVisitCountFromIE() + link.getVisitCountFromSafari() + link.getVisitCountFromOtherBrowser();
        response.setTotalVisitedCount(totalVisitCount);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllShortURLs(@RequestParam Map<String, String> params) {

        boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));


        List<LinkDTO> links = new ArrayList<>();
        if (params.isEmpty())  {

            if (!isAdmin) {
                return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
            }

            for (Link link : linkService.getAllLinks()) {
                LinkDTO tempLink = new LinkDTO();

                tempLink.setExpTime(link.getExpTime());
                tempLink.setUrl(link.getUrl());
                tempLink.setCode(domain + link.getCode());
                tempLink.setLinkId(link.getLinkId());
                tempLink.setUserName(link.getOwner().getUserName());
                tempLink.setDescription(link.getDescription());

                Integer totalVisitCount = link.getVisitCountFromChrome() + link.getVisitCountFromFirefox()
                        + link.getVisitCountFromIE() + link.getVisitCountFromSafari() + link.getVisitCountFromOtherBrowser();
                tempLink.setTotalVisitedCount(totalVisitCount);

                tempLink.setVisitedCountFromAndroid(link.getVisitCountFromAndroid());
                tempLink.setVisitedCountFromWindows(link.getVisitCountFromWindows());
                tempLink.setVisitedCountFromOsx(link.getVisitCountFromOsx());
                tempLink.setVisitedCountFromLinux(link.getVisitCountFromLinux());
                tempLink.setVisitedCountFromIOS(link.getVisitCountFromIOS());
                tempLink.setVisitedCountFromOtherOs(link.getVisitCountFromOtherOs());

                tempLink.setVisitedCountFromChrome(link.getVisitCountFromChrome());
                tempLink.setVisitedCountFromFirefox(link.getVisitCountFromFirefox());
                tempLink.setVisitedCountFromIe(link.getVisitCountFromIE());
                tempLink.setVisitedCountFromSafari(link.getVisitCountFromSafari());
                tempLink.setVisitedCountFromOtherBrowser(link.getVisitCountFromOtherBrowser());

                Integer visitCountFromComputer = link.getVisitCountFromLinux() + link.getVisitCountFromOsx()
                        + link.getVisitCountFromWindows() + link.getVisitCountFromOtherOs();

                Integer visitCountFromMobile = link.getVisitCountFromAndroid() + link.getVisitCountFromIOS();

                tempLink.setVisitedCountFromComputer(visitCountFromComputer);
                tempLink.setVisitedCountFromMobile(visitCountFromMobile);

                tempLink.setCreatedAt(link.getCreatedAt());

                links.add(tempLink);
            }
        }
        else {

            if (params.containsKey("userId")) {

                if (params.get("userId") == null){
                    return ResponseEntity.badRequest().body("userId cannot be empty.");
                }
                if (params.get("userId").length() == 0){
                    return ResponseEntity.badRequest().body("userId cannot be empty.");
                }

                Integer userId;

                try {
                    userId = Integer.parseInt(params.get("userId"));
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body("userId must be an integer.");
                }


                if (!isAdmin && !userId.equals(userService.getUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId())){
                    return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
                }

                List<Link> linksByUserId = linkService.getLinksByUserId(userId);

                if (linksByUserId.isEmpty()){
                    return ResponseEntity.notFound().build();
                }

                for (Link link : linksByUserId) {
                    LinkDTO tempLink = new LinkDTO();

                    tempLink.setExpTime(link.getExpTime());
                    tempLink.setUrl(link.getUrl());
                    tempLink.setCode(domain + link.getCode());
                    tempLink.setLinkId(link.getLinkId());
                    tempLink.setUserName(link.getOwner().getUserName());
                    tempLink.setDescription(link.getDescription());

                    Integer totalVisitCount = link.getVisitCountFromChrome() + link.getVisitCountFromFirefox()
                            + link.getVisitCountFromIE() + link.getVisitCountFromSafari() + link.getVisitCountFromOtherBrowser();
                    tempLink.setTotalVisitedCount(totalVisitCount);

                    tempLink.setVisitedCountFromAndroid(link.getVisitCountFromAndroid());
                    tempLink.setVisitedCountFromWindows(link.getVisitCountFromWindows());
                    tempLink.setVisitedCountFromOsx(link.getVisitCountFromOsx());
                    tempLink.setVisitedCountFromLinux(link.getVisitCountFromLinux());
                    tempLink.setVisitedCountFromIOS(link.getVisitCountFromIOS());
                    tempLink.setVisitedCountFromOtherOs(link.getVisitCountFromOtherOs());

                    tempLink.setVisitedCountFromChrome(link.getVisitCountFromChrome());
                    tempLink.setVisitedCountFromFirefox(link.getVisitCountFromFirefox());
                    tempLink.setVisitedCountFromIe(link.getVisitCountFromIE());
                    tempLink.setVisitedCountFromSafari(link.getVisitCountFromSafari());
                    tempLink.setVisitedCountFromOtherBrowser(link.getVisitCountFromOtherBrowser());

                    Integer visitCountFromComputer = link.getVisitCountFromLinux() + link.getVisitCountFromOsx()
                            + link.getVisitCountFromWindows() + link.getVisitCountFromOtherOs();

                    Integer visitCountFromMobile = link.getVisitCountFromAndroid() + link.getVisitCountFromIOS();

                    tempLink.setVisitedCountFromComputer(visitCountFromComputer);
                    tempLink.setVisitedCountFromMobile(visitCountFromMobile);


                    links.add(tempLink);
                }
            }
            else{
                return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
            }
        }

        return ResponseEntity.ok(links);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getShortURLById(@PathVariable("id") Integer id) {

        boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));


        if (!isAdmin && !isUser) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        Link link = linkService.getLinkById(id);


        if (link == null) {
            return ResponseEntity.notFound().build();
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(link.getOwner().getUserName()) && !isAdmin){
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        LinkDTO response = new LinkDTO();

        response.setExpTime(link.getExpTime());
        response.setUrl(link.getUrl());
        response.setCode(domain + link.getCode());
        response.setLinkId(link.getLinkId());
        response.setUserName(link.getOwner().getUserName());
        response.setDescription(link.getDescription());

        Integer totalVisitCount = link.getVisitCountFromChrome() + link.getVisitCountFromFirefox()
                + link.getVisitCountFromIE() + link.getVisitCountFromSafari() + link.getVisitCountFromOtherBrowser();
        response.setTotalVisitedCount(totalVisitCount);

        response.setVisitedCountFromAndroid(link.getVisitCountFromAndroid());
        response.setVisitedCountFromWindows(link.getVisitCountFromWindows());
        response.setVisitedCountFromOsx(link.getVisitCountFromOsx());
        response.setVisitedCountFromLinux(link.getVisitCountFromLinux());
        response.setVisitedCountFromIOS(link.getVisitCountFromIOS());
        response.setVisitedCountFromOtherOs(link.getVisitCountFromOtherOs());

        response.setVisitedCountFromChrome(link.getVisitCountFromChrome());
        response.setVisitedCountFromFirefox(link.getVisitCountFromFirefox());
        response.setVisitedCountFromIe(link.getVisitCountFromIE());
        response.setVisitedCountFromSafari(link.getVisitCountFromSafari());
        response.setVisitedCountFromOtherBrowser(link.getVisitCountFromOtherBrowser());

        Integer visitCountFromComputer = link.getVisitCountFromLinux() + link.getVisitCountFromOsx()
                + link.getVisitCountFromWindows() + link.getVisitCountFromOtherOs();

        Integer visitCountFromMobile = link.getVisitCountFromAndroid() + link.getVisitCountFromIOS();

        response.setVisitedCountFromComputer(visitCountFromComputer);
        response.setVisitedCountFromMobile(visitCountFromMobile);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteShortURLById(@PathVariable("id") int id) {
        boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin && !isUser) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        Link link = linkService.getLinkById(id);

        if (link == null){
            return ResponseEntity.notFound().build();
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(link.getOwner().getUserName()) && !isAdmin){
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        Long operationCode = linkService.deleteLinkById(id);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(username);

        if (operationCode == 0) {
            return ResponseEntity.notFound().build();
        }
        else if (operationCode == 1) {
            user.setTotalRightsUsed(user.getTotalRightsUsed() - 1);
            userService.save(user);
            return ResponseEntity.ok().body("Deletion Successful");
        }

        return ResponseEntity.badRequest().body("Unexpected Error");
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> updateShortURLById(@PathVariable("id") int id, @RequestBody @NonNull LinkDTO linkDTO) {

        UrlValidator urlValidator = new UrlValidator();

        boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER"));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if (!isAdmin && !isUser) {
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        Link link = linkService.getLinkById(id);

        if (link == null) {
            return ResponseEntity.notFound().build();
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(link.getOwner().getUserName()) && !isAdmin){
            return ResponseEntity.badRequest().body("You don't have authorization for this operation.");
        }

        if (linkDTO.getCode() != null){

            if (linkService.getLinkByCode(linkDTO.getCode()) == null) {
                if (linkDTO.getCode().length() == 0){
                    return ResponseEntity.badRequest().body("Code cannot be empty.");
                }
                linkDTO.setCode(linkDTO.getCode());
            }
            else {
                return ResponseEntity.badRequest().body("Custom URL already exists!");
            }
            link.setCode(linkDTO.getCode());
        }

        if (linkDTO.getExpTime() != null){
            link.setExpTime(linkDTO.getExpTime());
        }

        if (linkDTO.getUrl() != null){

            String url = linkDTO.getUrl();

            if (url.length() < 8) {
                url = "http://" + url;
            }
            else if (!url.substring(0, 8).equals("https://") && !url.substring(0, 7).equals("http://")) {
                url = "http://" + url;
            }

            linkDTO.setUrl(url);

            if (!urlValidator.isValid(linkDTO.getUrl())) {
                return ResponseEntity.badRequest().body("Given URL is invalid!");
            }

            link.setUrl(linkDTO.getUrl());
        }

        if (linkDTO.getDescription() != null){
            link.setDescription(linkDTO.getDescription());
        }

        link = linkService.updateLink(link);

        if (link == null){
            return ResponseEntity.badRequest().body("Wrong Input Format");
        }

        LinkDTO response = new LinkDTO();

        response.setExpTime(link.getExpTime());
        response.setUrl(link.getUrl());
        response.setCode(domain + link.getCode());
        response.setLinkId(link.getLinkId());
        response.setUserName(link.getOwner().getUserName());
        response.setDescription(link.getDescription());

        return ResponseEntity.ok(response);
    }

    private String generateRandomCode() {
        for (int i = 0; i < 10; i++) {
            String randomCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);

            if (linkService.getLinkByCode(randomCode) == null) {
                return randomCode;
            }
        }

        return null;
    }
}
