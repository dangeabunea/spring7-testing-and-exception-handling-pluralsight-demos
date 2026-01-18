package com.pluralsight.afm.service;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.FlightStatus;
import com.pluralsight.afm.domain.Location;
import com.pluralsight.afm.repository.FlightRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AlertService alertService;

    @InjectMocks
    private FlightService flightService;

    @Nested
    class CancelFlight {
        @Test
        void should_cancel_existing_flight() {
            // Arrange
            var existingFlight = new Flight(
                    UUID.randomUUID(),
                    "LH2030",
                    new Location("ED", "Berlin", "EDDB"),
                    new Location("EH", "Amsterdam", "EHAM"),
                    UUID.randomUUID(),
                    LocalDateTime.of(2026, 1, 21, 9, 15),
                    90
            );

            when(flightRepository.findByFlightNumber("LH2030"))
                    .thenReturn(Optional.of(existingFlight));

            // Act
            flightService.cancelFlight("LH2030");

            // Assert
            assertThat(existingFlight.getStatus()).isEqualTo(FlightStatus.CANCELLED);
            verify(flightRepository).save(argThat(f -> f.getStatus() == FlightStatus.CANCELLED));
        }
    }
}