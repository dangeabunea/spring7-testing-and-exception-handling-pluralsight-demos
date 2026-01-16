package com.pluralsight.afm.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a flight alert for a specific country.
 * Alerts are temporal and can impact flights departing from or arriving to the affected country.
 * If endDateTime is null, the alert is considered ongoing.
 * <p>
 * This class is immutable except for the endDateTime, which can be set to close an alert.
 */
public class CountryFlightAlert {
    private UUID id;
    private String icaoCountryCode;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;

    protected CountryFlightAlert() {}

    public CountryFlightAlert(UUID id,
                              String icaoCountryCode,
                              LocalDateTime startDateTime,
                              String description) {
        this(id, icaoCountryCode, startDateTime, null, description);
    }

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
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (endDateTime != null && endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("End date/time cannot be before start date/time");
        }

        this.icaoCountryCode = icaoCountryCode;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
    }

    /**
     * Checks if this alert is active at the given time.
     * An alert is active if the check time is between start and end dates.
     * If endDateTime is null, the alert is considered ongoing (active indefinitely).
     */
    public boolean isActiveAt(LocalDateTime checkTime) {
        if (checkTime == null) {
            throw new IllegalArgumentException("Check time cannot be null");
        }

        boolean afterStart = !checkTime.isBefore(startDateTime);
        boolean beforeEnd = endDateTime == null || !checkTime.isAfter(endDateTime);

        return afterStart && beforeEnd;
    }

    public void close(LocalDateTime endDateTime) {
        if (this.endDateTime != null) {
            throw new IllegalStateException("Alert is already closed");
        }
        if (endDateTime == null) {
            throw new IllegalArgumentException("End date/time cannot be null");
        }
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("End date/time cannot be before start date/time");
        }
        this.endDateTime = endDateTime;
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
