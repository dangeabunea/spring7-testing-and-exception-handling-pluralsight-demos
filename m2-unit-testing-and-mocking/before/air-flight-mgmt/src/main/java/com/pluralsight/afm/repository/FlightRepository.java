package com.pluralsight.afm.repository;

import com.pluralsight.afm.domain.Flight;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightRepository {

    Flight save(Flight flight);

    Optional<Flight> findById(UUID id);

    Optional<Flight> findByFlightNumber(String flightNumber);

    void deleteById(Long id);
}
