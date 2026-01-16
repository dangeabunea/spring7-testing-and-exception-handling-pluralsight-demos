package com.pluralsight.afm;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FlightDurationServiceTest {
    @Test
    void should_calculate_arrival_time() {
        // Arrange
        var flightDurationService = new FlightDurationService();
        var departureTime = LocalDateTime.of(2026, 1, 1, 8, 0);
        var fourteenHours = 14 * 60;

        // Act
        var result = flightDurationService.calculateFlightDuration(departureTime, fourteenHours);

        // Assert
        assertThat(result).isEqualTo(LocalDateTime.of(2026, 1, 1, 22, 0));
    }
}