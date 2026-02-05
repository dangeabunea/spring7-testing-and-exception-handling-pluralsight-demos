package com.pluralsight.afm.controller;

import com.pluralsight.afm.repository.FlightRepository;
import com.pluralsight.afm.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

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