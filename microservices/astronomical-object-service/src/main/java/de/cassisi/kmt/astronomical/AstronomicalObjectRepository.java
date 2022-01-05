package de.cassisi.kmt.astronomical;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AstronomicalObjectRepository extends JpaRepository<AstronomicalObject, Long> {

    List<AstronomicalObject> findAstronomicalObjectsByDeclinationBetweenAndRightAscensionBetween(
            double dec_min,
            double dec_max,
            double rec_min,
            double rec_max);

}
