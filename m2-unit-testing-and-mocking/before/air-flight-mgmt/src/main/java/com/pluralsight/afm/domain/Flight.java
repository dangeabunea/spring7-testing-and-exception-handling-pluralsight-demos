package com.pluralsight.afm.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a flight with scheduling and status management.
 * Contains business logic for flight operations like rescheduling and arrival time calculation.
 */
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    private UUID id;

    private String flightNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "icaoCountryCode", column = @Column(name = "departure_country_code")),
            @AttributeOverride(name = "city", column = @Column(name = "departure_city")),
            @AttributeOverride(name = "icaoAirportCode", column = @Column(name = "departure_airport_code"))
    })
    private Location departure;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "icaoCountryCode", column = @Column(name = "destination_country_code")),
            @AttributeOverride(name = "city", column = @Column(name = "destination_city")),
            @AttributeOverride(name = "icaoAirportCode", column = @Column(name = "destination_airport_code"))
    })
    private Location destination;

    private UUID aircraftId;

    private LocalDateTime scheduledDepartureTime;

    private int durationInMinutes;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private UUID affectedByAlertId;

    protected Flight() {}

    public Flight(UUID id,
                  String flightNumber,
                  Location departure,
                  Location destination,
                  UUID aircraftId,
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
        return departure.getIcaoCountryCode().equals(icaoCountryPrefix) ||
               destination.getIcaoCountryCode().equals(icaoCountryPrefix);
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

    public UUID getAircraftId() {
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
