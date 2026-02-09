package com.pluralsight.afm.repository;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.FlightStatus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/test-data/flights-seed.sql")
class FlightRepositoryIntegrationTest {

    @Autowired
    private FlightRepository flightRepository;

    @Nested
    class FindAffectedFlights {
        @Test
        void should_return_flights_affected_by_alert() {
            // Arrange
            var countryCode = "LF"; // France
            var startTime = LocalDateTime.of(2026, 6, 15, 0, 0);
            var endTime = LocalDateTime.of(2026, 6, 15, 23, 59);
            var statuses = List.of(FlightStatus.SCHEDULED, FlightStatus.RESCHEDULED);

            // Act
            List<Flight> affectedFlights = flightRepository.findAffectedFlights(
                    countryCode, startTime, endTime, statuses);

            // Assert
            assertThat(affectedFlights).hasSize(2);

            var flightNumbers = affectedFlights.stream()
                    .map(Flight::getFlightNumber)
                    .toList();
            assertThat(flightNumbers).contains("AF101");
            assertThat(flightNumbers).contains("BA202");
        }
    }
}
