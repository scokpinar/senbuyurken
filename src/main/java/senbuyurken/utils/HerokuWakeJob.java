package senbuyurken.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by SametCokpinar on 24/12/15.
 */

public class HerokuWakeJob {

    @Scheduled(fixedRate = 1200000)
    public void schedulerTest() {
        System.out.println("Scheduler Works at" + new Date().toString());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
        String url = "https://afternoon-citadel-9635.herokuapp.com/rest/appUtilityRest/wake";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

    }

}
