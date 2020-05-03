package bilshort.link.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@RequestMapping("r")
@RestController
public class RedirectController {

    private static RestTemplate restTemplate;

    private static String redirectUrl;

    @PostConstruct
    private void init(){
        restTemplate = new RestTemplate();
        redirectUrl = "http://localhost:8082/r";
    }

    @GetMapping("{code}")
    public ResponseEntity<?> redirectUrl(@PathVariable("code") String code, @RequestHeader(value = "User-Agent") String userAgent) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", userAgent);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String url = redirectUrl + "/" + code;
        ResponseEntity responseEntity;

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(responseEntity.getHeaders()).build();
    }
}