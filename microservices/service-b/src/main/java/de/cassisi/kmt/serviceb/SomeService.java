package de.cassisi.kmt.serviceb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class SomeService {

    private final RestTemplate restTemplate;

    public SomeService(@LoadBalanced RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/call")
    public ResponseEntity<String> test() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("http://calculator-service/");
        return restTemplate.getForEntity(builder.toUriString(), String.class);
    }
}
