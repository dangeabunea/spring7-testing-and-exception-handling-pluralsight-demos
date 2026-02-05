package com.pluralsight.afm.controller;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.FlightStatus;
import com.pluralsight.afm.domain.Location;
import com.pluralsight.afm.dto.CreateFlightDto;
import com.pluralsight.afm.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test slice for FlightController using @WebMvcTest.
 * <p>
 * Key differences from @SpringBootTest:
 * - Only loads web layer (controller, filters, converters)
 * - No database, no JPA, no real services
 * - All dependencies must be mocked
 * - Much faster startup
 * - Tests controller logic in isolation
 */

class FlightControllerIntegrationTest {

}