package de.cassisi.kmt.forecast.controller;

import de.cassisi.kmt.forecast.dto.AstronomicalObjectDTO;
import de.cassisi.kmt.forecast.dto.ForecastDTO;
import de.cassisi.kmt.forecast.dto.LocationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@RestController
public class ForecastController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForecastController.class);

    private final WebClient.Builder webClientBuilder;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public ForecastController(WebClient.Builder webClientBuilder, CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
        this.webClientBuilder = webClientBuilder;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @GetMapping("/today")
    public ResponseEntity<ForecastDTO> getForecast(@RequestParam String location) {
        LOGGER.info("Forecasting for location: " + location);

        // get location information
        LocationDTO loc = circuitBreakerFactory.create("location-service").run(
                () -> webClientBuilder.build()
                        .get()
                        .uri("http://location-service/location/{name}", location)
                        .retrieve()
                        .bodyToMono(LocationDTO.class)
                        .block(),
                throwable -> LocationDTO.ofDefault()
        );

        // calculate min/max astronomical object coordinates for today

        String locationName = loc.getName();
        double latitude = loc.getLatitude();
        double longitude = loc.getLongitude();

        // for now, we just include all objects...
        double decMin = 0;
        double decMax = 90;
        double recMin = 0;
        double recMax = 360;

        // get astronomical objects for specified ranges
        AstronomicalObjectDTO[] results = circuitBreakerFactory.create("astronomical-object-service").run(
                () -> webClientBuilder
                        .baseUrl("http://astronomical-object-service/get")
                        .build()
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("dec_min", decMin)
                                .queryParam("dec_max", decMax)
                                .queryParam("rec_min", recMin)
                                .queryParam("rec_max", recMax)
                                .build()
                        )
                        .retrieve()
                        .bodyToMono(AstronomicalObjectDTO[].class)
                        .block(),
                throwable -> {
                    LOGGER.trace("FAILURE: " + throwable);
                    return new AstronomicalObjectDTO[]{};
                }
        );

        ForecastDTO forecast = new ForecastDTO();
        forecast.setLocationName(locationName);
        forecast.setLatitude(latitude);
        forecast.setLongitude(longitude);
        forecast.setAstronomicalObjects(Arrays.asList(results));
        return ResponseEntity.ok(forecast);
    }

}
