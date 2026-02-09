package com.pluralsight.afm.controller;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.FlightStatus;
import com.pluralsight.afm.dto.CreateFlightDto;
import com.pluralsight.afm.repository.FlightRepository;
import com.pluralsight.afm.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Full integration test for FlightController using @SpringBootTest.
 *
 * Key characteristics:
 * - Loads the entire Spring application context (controllers, services, repositories)
 * - Starts an embedded server on a random port
 * - Uses real H2 database with Hibernate for persistence
 * - Can verify data is actually persisted to the database
 * - Can use @MockitoBean to mock external dependencies (AlertService)
 * - Slower startup (~5-6 seconds) but tests real component interactions
 * - Data persists across tests - use @BeforeEach cleanup for test isolation
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class FlightControllerFullIntegrationTest {
    @Autowired
    private FlightRepository flightRepository;

    @MockitoBean
    private AlertService alertService;

    @Autowired
    private RestTestClient restTestClient;

    @BeforeEach
    void setUp() {
        this.flightRepository.deleteAll();
    }

    @Nested
    class CreateFlight {
        @Test
        void should_create_flight_successfully_when_no_active_alerts() {
            // Arrange
            var request = new CreateFlightDto(
                    "LH2030",
                    "ED",
                    "Berlin",
                    "EDDB",
                    "EH",
                    "Amsterdam",
                    "EHAM",
                    UUID.randomUUID(),
                    LocalDateTime.of(2026, 6, 15, 10, 30),
                    90
            );

            when(alertService.findActiveAlertForCountryAt(any(), any()))
                    .thenReturn(Optional.empty());

            // Act & Assert
            restTestClient.post()
                    .uri("/api/flights")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(Flight.class)
                    .value(flight -> {
                        assertThat(flight.getFlightNumber()).isEqualTo("LH2030");
                        assertThat(flight.getStatus()).isEqualTo(FlightStatus.SCHEDULED);
                        assertThat(flight.getDeparture().getIcaoCountryCode()).isEqualTo("ED");
                        assertThat(flight.getDeparture().getCity()).isEqualTo("Berlin");
                        assertThat(flight.getDeparture().getIcaoAirportCode()).isEqualTo("EDDB");
                        assertThat(flight.getDestination().getIcaoCountryCode()).isEqualTo("EH");
                        assertThat(flight.getDestination().getCity()).isEqualTo("Amsterdam");
                        assertThat(flight.getDestination().getIcaoAirportCode()).isEqualTo("EHAM");
                        assertThat(flight.getDurationInMinutes()).isEqualTo(90);
                    });

            var flight = flightRepository.findByFlightNumber("LH2030").get();
            assertThat(flight).isNotNull();
        }
    }
}
