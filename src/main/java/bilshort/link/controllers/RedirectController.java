package bilshort.link.controllers;

import bilshort.link.models.Link;
import bilshort.link.services.LinkService;
import eu.bitwalker.useragentutils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RequestMapping("r")
@RestController
public class RedirectController {

    @Autowired
    private LinkService linkService;

    @GetMapping("{code}")
    public ResponseEntity<String> redirectUrl(@PathVariable("code") String code, @RequestHeader(value = "User-Agent") String userAgent) {
        HttpHeaders responseHeaders = new HttpHeaders();

//        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
//        System.out.println("Browser: " + ua.getBrowser().getName());
//        System.out.println("OS: " + ua.getOperatingSystem().getName());
//        System.out.println("Device: " + ua.getOperatingSystem().getDeviceType().getName());

        Link link = linkService.getLinkByCode(code);

        if (link == null) {
            return ResponseEntity.notFound().build();
        }

        link.setVisitCount(link.getVisitCount() + 1);
        linkService.updateLink(link);

        try {
            responseHeaders.setLocation(new URI(link.getUrl()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(responseHeaders).build();
    }
}