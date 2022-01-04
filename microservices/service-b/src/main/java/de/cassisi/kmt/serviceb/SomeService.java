package de.cassisi.kmt.serviceb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class SomeService {

    private final WebClient.Builder builder;

    public SomeService(WebClient.Builder builder) {
        this.builder = builder;
    }

    @GetMapping("/call")
    public Mono<String> callMe() {
        return builder
                .build()
                .get()
                .uri("http://calculator-service/")
                .retrieve()
                .bodyToMono(String.class);
    }
}
