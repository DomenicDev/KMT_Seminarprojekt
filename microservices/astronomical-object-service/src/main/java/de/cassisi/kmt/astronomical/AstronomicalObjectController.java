package de.cassisi.kmt.astronomical;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AstronomicalObjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AstronomicalObjectController.class);

    private final AstronomicalObjectRepository repository;
    private final ModelMapper modelMapper = new ModelMapper();

    public AstronomicalObjectController(AstronomicalObjectRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/add")
    public void postAstronomicalObject(@RequestBody AstronomicalObjectDTO dto) {
        AstronomicalObject object = new AstronomicalObject();
        object.setName(dto.getName());
        object.setDeclination(dto.getDeclination());
        object.setRightAscension(dto.getRightAscension());
        object.setMagnitude(dto.getMagnitude());

        // save to database
        AstronomicalObject savedEntity = repository.save(object);
        LOGGER.info("object saved: " + savedEntity);
    }

    @GetMapping("/all")
    public List<AstronomicalObjectDTO> getAstronomicalObjects() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/get")
    public List<AstronomicalObjectDTO> getAstronomicalObjectsWithRange(
            @RequestParam("dec_min") double declinationMin,
            @RequestParam("dec_max") double declinationMax,
            @RequestParam("rec_min") double rightAscensionMin,
            @RequestParam("rec_max") double rightAscensionMax) {
        LOGGER.info("Fetching objects in range: " + declinationMin + "-" + declinationMax + ", " + rightAscensionMin + "-" + rightAscensionMax);
        return repository.findAstronomicalObjectsByDeclinationBetweenAndRightAscensionBetween(
                        declinationMin,
                        declinationMax,
                        rightAscensionMin,
                        rightAscensionMax)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AstronomicalObjectDTO toDTO(AstronomicalObject entity) {
        return modelMapper.map(entity, AstronomicalObjectDTO.class);
    }

}
