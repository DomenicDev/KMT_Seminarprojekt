package de.cassisi.kmt.astronomical;

import lombok.Data;

@Data
public class AstronomicalObjectDTO {

    private String name;
    private double declination;
    private double rightAscension;
    private double magnitude;

}
