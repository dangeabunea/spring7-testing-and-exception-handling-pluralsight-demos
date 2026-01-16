package com.pluralsight.afm.dto;

import java.time.LocalDateTime;

/**
 * Data transfer object for creating a new flight.
 */
public record CreateFlightRequestDto(
        String flightNumber,
        String departureIcaoCountryPrefix,
        String departureCity,
        String departureIcaoAirportCode,
        String destinationIcaoCountryPrefix,
        String destinationCity,
        String destinationIcaoAirportCode,
        String aircraftId,
        LocalDateTime scheduledDepartureTime,
        int durationInMinutes
) {
}
