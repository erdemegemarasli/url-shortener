package bilshort.link.controllers;

import eu.bitwalker.useragentutils.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RequestMapping("r")
@RestController
public class RedirectController {

    @GetMapping("{code}")
    public ResponseEntity<String> redirectUrl(@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("code") String code) {

        System.out.println("[USER-AGENT]: ");
        System.out.println(userAgent);
        HttpHeaders responseHeaders = new HttpHeaders();

        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        System.out.println("Browser: " + ua.getBrowser().getName());
        System.out.println("OS: " + ua.getOperatingSystem().getName());
        System.out.println("Device: " + ua.getOperatingSystem().getDeviceType().getName());

        try {
            URI address = new URI("https://www.google.com.tr");
            responseHeaders.setLocation(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(responseHeaders).build();
    }
}