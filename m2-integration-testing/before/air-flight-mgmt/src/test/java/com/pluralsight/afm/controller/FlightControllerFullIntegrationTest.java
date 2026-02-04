package com.pluralsight.afm.controller;

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