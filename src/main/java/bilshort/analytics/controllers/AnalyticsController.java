package bilshort.analytics.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
public class AnalyticsController {

    private static RestTemplate restTemplate;
    private static String browserURL;
    private static String osURL;
    private static String deviceURL;

    @Value("${analytics.url}")
    private String url;

    @PostConstruct
    private void init(){
        restTemplate = new RestTemplate();

        String browserEndpoint = "/count/browser";
        browserURL = url + browserEndpoint;

        String osEndpoint = "/count/os";
        osURL = url + osEndpoint;

        String deviceEndpoint = "/count/device";
        deviceURL = url + deviceEndpoint;
    }


    @GetMapping("/count/browser")
    public ResponseEntity<?> getBrowserCountBetweenDays(@RequestParam Map<String, String> params) {
        return sendRequest(params, browserURL);
    }

    @GetMapping("/count/os")
    public ResponseEntity<?> getOsCountBetweenDays(@RequestParam Map<String, String> params) {
        return sendRequest(params, osURL);
    }

    @GetMapping("/count/device")
    public ResponseEntity<?> getDeviceCountBetweenDays(@RequestParam Map<String, String> params) {
        return sendRequest(params, deviceURL);
    }

    private ResponseEntity<?> sendRequest(@RequestParam Map<String, String> params, String requestURL) {
        if (!params.containsKey("startDate") || !params.containsKey("endDate") || params.get("startDate") == null || params.get("endDate") == null) {
            return ResponseEntity.badRequest().body("Wrong time format.");
        }

        Long startDate = null;
        Long endDate = null;

        try {
            startDate = Long.parseLong(params.get("startDate"));
            endDate = Long.parseLong(params.get("endDate"));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Wrong time format.");
        }

        String param = "?startDate=" + startDate + "&endDate=" + endDate;
        String url = requestURL + param;

        return ResponseEntity.ok(restTemplate.getForEntity(url, Object.class).getBody());
    }

}
