package com.pluralsight.afm.exception;

/**
 * Exception thrown when a flight cannot be found.
 */
public class FlightNotFoundException extends RuntimeException {

    private final Long flightId;

    public FlightNotFoundException(Long flightId) {
        super("Flight not found with id: " + flightId);
        this.flightId = flightId;
    }

    public FlightNotFoundException(String flightNumber) {
        super("Flight not found with flight number: " + flightNumber);
        this.flightId = null;
    }

    public Long getFlightId() {
        return flightId;
    }
}
