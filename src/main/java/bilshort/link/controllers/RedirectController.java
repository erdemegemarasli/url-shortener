package bilshort.link.controllers;

import bilshort.jedis.JedisPublisher;
import bilshort.jedis.KeyExpiredListener;
import bilshort.link.models.Link;
import bilshort.link.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RequestMapping("r")
@RestController
public class RedirectController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private JedisPublisher jedisPublisher;

    @Autowired
    KeyExpiredListener keyExpiredListener;

    @PostConstruct
    private void init(){
        new Thread(() -> {
            JedisPool pool = new JedisPool(new JedisPoolConfig());
            Jedis j = pool.getResource();
            j.psubscribe(keyExpiredListener, "*");
        }).start();
    }

    @GetMapping("{code}")
    public ResponseEntity<String> redirectUrl(@PathVariable("code") String code, @RequestHeader(value = "User-Agent") String userAgent) {
        HttpHeaders responseHeaders = new HttpHeaders();
        Optional<String> url = jedisPublisher.getCachedLink(code);

        if (url.isPresent()){
            try {
                responseHeaders.setLocation(new URI(url.get()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else {
            Link link = linkService.getLinkByCode(code);

            if (link == null) {
                return ResponseEntity.notFound().build();
            }

            jedisPublisher.cacheLink(link.getCode(), link.getUrl(), link.getVisitCount() + 1);

            try {
                responseHeaders.setLocation(new URI(link.getUrl()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(responseHeaders).build();
    }

//    KEEP THIS FOR SPEED COMPARISON
//    @GetMapping("{code}")
//    public ResponseEntity<String> redirectUrl(@PathVariable("code") String code, @RequestHeader(value = "User-Agent") String userAgent) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//
////        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
////        System.out.println("Browser: " + ua.getBrowser().getName());
////        System.out.println("OS: " + ua.getOperatingSystem().getName());
////        System.out.println("Device: " + ua.getOperatingSystem().getDeviceType().getName());
//
//        Link link = linkService.getLinkByCode(code);
//
//        if (link == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        link.setVisitCount(link.getVisitCount() + 1);
//        linkService.updateLink(link);
//
//        try {
//            responseHeaders.setLocation(new URI(link.getUrl()));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(responseHeaders).build();
//    }
}