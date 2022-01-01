package de.cassisi.kmt.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/calculator/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://calculator-service"))
                .build();
    }


}
