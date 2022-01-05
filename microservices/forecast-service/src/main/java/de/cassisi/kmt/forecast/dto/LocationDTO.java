package de.cassisi.kmt.forecast.dto;

import lombok.Data;

@Data
public class LocationDTO {

    private String name;
    private double longitude;
    private double latitude;

    public static LocationDTO ofDefault() {
        LocationDTO loc = new LocationDTO();
        loc.name = "Furtwangen";
        loc.longitude = 8.201419;
        loc.latitude = 48.050144;
        return loc;
    }

}
