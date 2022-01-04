package de.cassisi.kmt.serviceb;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class CircuitBreakerTest {

    private final CircuitBreakerFactory factory;
    private final WebClient.Builder builder;

    public CircuitBreakerTest(CircuitBreakerFactory factory, WebClient.Builder builder) {
        this.factory = factory;
        this.builder = builder;
    }

    @GetMapping("/test")
    public String test() {
        return factory.create("test").run(
                () -> builder.build()
                        .get()
                        .uri("http://calculator-service/")
                        .retrieve()
                        .bodyToMono(String.class).block(),
                throwable -> "This is the fallback value!"
        );
    }
}
