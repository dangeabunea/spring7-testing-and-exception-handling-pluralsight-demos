package com.pluralsight.afm.controller;

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

class FlightControllerIntegrationTest {

}