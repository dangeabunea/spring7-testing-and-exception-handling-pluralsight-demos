package com.pluralsight.afm.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data transfer object for creating a new flight.
 */
public record CreateFlightRequestDto(
        String flightNumber,
        String departureIcaoCountryCode,
        String departureCity,
        String departureIcaoAirportCode,
        String destinationIcaoCountryCode,
        String destinationCity,
        String destinationIcaoAirportCode,
        UUID aircraftId,
        LocalDateTime scheduledDepartureTime,
        int durationInMinutes
) {
}
