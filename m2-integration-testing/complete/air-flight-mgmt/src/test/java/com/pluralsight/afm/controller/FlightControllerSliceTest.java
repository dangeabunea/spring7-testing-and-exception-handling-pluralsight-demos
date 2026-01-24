package com.pluralsight.afm.controller;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.FlightStatus;
import com.pluralsight.afm.domain.Location;
import com.pluralsight.afm.dto.CreateFlightDto;
import com.pluralsight.afm.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test slice for FlightController using @WebMvcTest.
 *
 * Key differences from @SpringBootTest:
 * - Only loads web layer (controller, filters, converters)
 * - No database, no JPA, no real services
 * - All dependencies must be mocked
 * - Much faster startup
 * - Tests controller logic in isolation
 */
@WebMvcTest(FlightController.class)
class FlightControllerSliceTest {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private FlightService flightService;

    private RestTestClient restTestClient;

    @BeforeEach
    void setUp() {
        restTestClient = RestTestClient.bindToApplicationContext(context).build();
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

            // Create the expected flight that the service would return
            var expectedFlight = new Flight(
                    UUID.randomUUID(),
                    "LH2030",
                    new Location("ED", "Berlin", "EDDB"),
                    new Location("EH", "Amsterdam", "EHAM"),
                    request.aircraftId(),
                    LocalDateTime.of(2026, 6, 15, 10, 30),
                    90
            );

            when(flightService.createFlight(any(CreateFlightDto.class)))
                    .thenReturn(expectedFlight);

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

            // Verify the controller called the service
            verify(flightService).createFlight(any(CreateFlightDto.class));
        }
    }
}
