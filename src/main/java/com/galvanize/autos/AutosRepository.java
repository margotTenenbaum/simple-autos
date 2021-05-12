package com.galvanize.autos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutosRepository extends JpaRepository<Auto, Long> {
    List<Auto> findByColorContainsAndMakeContains(String color, String make);

    List<Auto> findByColorContains(String color);

    List<Auto> findByMakeContains(String make);

    Optional<Auto> findByVin(String vin);
}
