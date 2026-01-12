package com.pluralsight.afm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfig.class)
class FlightDurationServiceIntegrationTest {

    @Autowired
    private FlightDurationService flightService;

    @Test
    void should_calculate_flight_duration() {
        // Arrange
        var departureTime = LocalDateTime.of(2026, 1, 1, 8, 0);

        // Act
        var arrivalTime = flightService.calculateFlightDuration(departureTime, 14 * 60);

        // Assert
        var expectedArrivalTime = LocalDateTime.of(2026, 1, 1, 22, 0);
        assertEquals(expectedArrivalTime, arrivalTime);
    }
}