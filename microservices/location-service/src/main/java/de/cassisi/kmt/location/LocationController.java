package de.cassisi.kmt.location;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class LocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    private final LocationRepository repository;
    private final ModelMapper mapper = new ModelMapper();

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/location")
    public void addLocation(@RequestBody LocationDTO locationDTO) {
        Location entity = mapper.map(locationDTO, Location.class);
        LOGGER.info("Saving location: " + entity);
        repository.save(entity);
    }

    @GetMapping("/location/{name}")
    public ResponseEntity<LocationDTO> getLocationByName(@PathVariable String name) {
        Optional<Location> optional = repository.findByName(name);
        LOGGER.info("Reading optional Location from database: " + optional);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Location location = optional.get();
        return ResponseEntity.ok(mapper.map(location, LocationDTO.class));
    }
}
