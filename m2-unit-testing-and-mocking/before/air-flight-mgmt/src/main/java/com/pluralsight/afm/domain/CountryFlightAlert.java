package com.pluralsight.afm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a flight alert for a specific country.
 * Alerts are temporal (have start and end times) and can impact flights
 * departing from or arriving to the affected country.
 */
@Entity
@Table(name = "country_flight_alerts")
public class CountryFlightAlert {

    @Id
    private UUID id;

    private String icaoCountryCode;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;

    protected CountryFlightAlert() {}

    public CountryFlightAlert(UUID id,
                              String icaoCountryCode,
                              LocalDateTime startDateTime,
                              LocalDateTime endDateTime,
                              String description) {
        if (icaoCountryCode == null || icaoCountryCode.isBlank()) {
            throw new IllegalArgumentException("ICAO country code cannot be null or blank");
        }
        if (startDateTime == null) {
            throw new IllegalArgumentException("Start date/time cannot be null");
        }
        if (endDateTime == null) {
            throw new IllegalArgumentException("End date/time cannot be null");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("End date/time cannot be before start date/time");
        }

        this.id = id;
        this.icaoCountryCode = icaoCountryCode;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
    }

    /**
     * Checks if this alert is active at the given time.
     * An alert is active if the check time is between start and end dates (inclusive).
     */
    public boolean isActiveAt(LocalDateTime checkTime) {
        if (checkTime == null) {
            throw new IllegalArgumentException("Check time cannot be null");
        }

        boolean afterStart = !checkTime.isBefore(startDateTime);
        boolean beforeEnd = !checkTime.isAfter(endDateTime);

        return afterStart && beforeEnd;
    }

    public long getDurationInHours() {
        return Duration.between(startDateTime, endDateTime).toHours();
    }

    public long getDurationInMinutes() {
        return Duration.between(startDateTime, endDateTime).toMinutes();
    }

    public UUID getId() {
        return id;
    }

    public String getIcaoCountryCode() {
        return icaoCountryCode;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getDescription() {
        return description;
    }
}
