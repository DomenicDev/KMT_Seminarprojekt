package de.cassisi.kmt.astronomical;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AstronomicalObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double declination; // -90° to +90°
    private double rightAscension; //0° to 360°
    private double magnitude;

}
