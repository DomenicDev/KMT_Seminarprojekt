package de.cassisi.kmt.forecast.dto;

import lombok.Data;

import java.util.List;

@Data
public class ForecastDTO {

    private String locationName;
    private double longitude;
    private double latitude;

    private List<AstronomicalObjectDTO> astronomicalObjects;

}
