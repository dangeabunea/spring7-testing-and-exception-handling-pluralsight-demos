package com.pluralsight.afm;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FlightDurationService {
    public LocalDateTime calculateFlightDuration(LocalDateTime departureHour, int durationMin) {
        if (departureHour == null) {
            throw new IllegalArgumentException("departureHour must not be null");
        }
        if (durationMin < 0) {
            throw new IllegalArgumentException("durationMin must be non-negative");
        }

        return departureHour.plusMinutes(durationMin);
    }
}