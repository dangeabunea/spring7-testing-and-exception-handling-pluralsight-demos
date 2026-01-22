package com.pluralsight.afm;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FlightDurationService {
    public LocalDateTime calculateFlightDuration(LocalDateTime departureTime, int durationMin) {
        if (departureTime == null) {
            throw new IllegalArgumentException("departureTime must not be null");
        }
        if (durationMin < 0) {
            throw new IllegalArgumentException("durationMin must be non-negative");
        }

        return departureTime.plusMinutes(durationMin);
    }
}