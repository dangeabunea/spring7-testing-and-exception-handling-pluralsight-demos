package com.pluralsight.afm.exception;

import java.util.UUID;

/**
 * Exception thrown when a flight cannot be found.
 */
public class FlightNotFoundException extends RuntimeException {
    private final UUID flightId;

    public FlightNotFoundException(UUID flightId) {
        super("Flight not found with id: " + flightId);
        this.flightId = flightId;
    }

    public FlightNotFoundException(String flightNumber) {
        super("Flight not found with flight number: " + flightNumber);
        this.flightId = null;
    }

    public UUID getFlightId() {
        return flightId;
    }
}
