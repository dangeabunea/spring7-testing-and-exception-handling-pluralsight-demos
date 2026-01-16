package com.pluralsight.afm.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a flight with scheduling and status management.
 * Contains business logic for flight operations like rescheduling and arrival time calculation.
 */
public class Flight {
    private UUID id;
    private String flightNumber;
    private Location departure;
    private Location destination;
    private String aircraftId;
    private LocalDateTime scheduledDepartureTime;
    private int durationInMinutes;
    private FlightStatus status;
    private UUID affectedByAlertId;

    protected Flight() {}

    public Flight(UUID id,
                  String flightNumber,
                  Location departure,
                  Location destination,
                  String aircraftId,
                  LocalDateTime scheduledDepartureTime,
                  int durationInMinutes) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.aircraftId = aircraftId;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.durationInMinutes = durationInMinutes;
        this.status = FlightStatus.SCHEDULED;
    }

    public LocalDateTime calculateArrivalTime() {
        if (scheduledDepartureTime == null) {
            throw new IllegalStateException("Cannot calculate arrival time without scheduled departure time");
        }
        return scheduledDepartureTime.plusMinutes(durationInMinutes);
    }

    /**
     * Reschedules the flight to a new departure time.
     * Only flights that are SCHEDULED or RESCHEDULED can be rescheduled.
     */
    public void reschedule(LocalDateTime newDepartureTime) {
        if (status == FlightStatus.COMPLETED) {
            throw new IllegalStateException("Cannot reschedule a completed flight");
        }
        if (status == FlightStatus.CANCELLED) {
            throw new IllegalStateException("Cannot reschedule a cancelled flight");
        }
        this.scheduledDepartureTime = newDepartureTime;
        this.status = FlightStatus.RESCHEDULED;
    }

    /**
     * Cancels the flight.
     * Only flights that are SCHEDULED or RESCHEDULED can be cancelled.
     */
    public void cancel() {
        if (status == FlightStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed flight");
        }
        if (status == FlightStatus.CANCELLED) {
            throw new IllegalStateException("Flight is already cancelled");
        }
        this.status = FlightStatus.CANCELLED;
    }

    /**
     * Marks the flight as completed.
     * Only flights that are SCHEDULED or RESCHEDULED can be completed.
     */
    public void complete() {
        if (status == FlightStatus.CANCELLED) {
            throw new IllegalStateException("Cannot complete a cancelled flight");
        }
        if (status == FlightStatus.COMPLETED) {
            throw new IllegalStateException("Flight is already completed");
        }
        this.status = FlightStatus.COMPLETED;
    }

    public boolean involvesCountry(String icaoCountryPrefix) {
        return departure.icaoCountryCode().equals(icaoCountryPrefix) ||
               destination.icaoCountryCode().equals(icaoCountryPrefix);
    }

    public UUID getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Location getDeparture() {
        return departure;
    }

    public Location getDestination() {
        return destination;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public LocalDateTime getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public UUID getAffectedByAlertId() {
        return affectedByAlertId;
    }

    public void setAffectedByAlertId(UUID affectedByAlertId) {
        this.affectedByAlertId = affectedByAlertId;
    }
}
