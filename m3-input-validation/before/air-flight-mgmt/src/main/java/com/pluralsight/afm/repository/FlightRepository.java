package com.pluralsight.afm.repository;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    @Query("""
            SELECT f FROM Flight f
            WHERE (f.departure.icaoCountryCode = :countryCode OR f.destination.icaoCountryCode = :countryCode)
            AND f.scheduledDepartureTime BETWEEN :startTime AND :endTime
            AND f.status IN :statuses
            """)
    List<Flight> findAffectedFlights(
            @Param("countryCode") String icaoCountryCode,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("statuses") List<FlightStatus> statuses);
}
