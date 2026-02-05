package com.pluralsight.afm.controller;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.FlightStatus;
import com.pluralsight.afm.dto.CreateFlightDto;
import com.pluralsight.afm.repository.FlightRepository;
import com.pluralsight.afm.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
class FlightControllerFullIntegrationTest {
}