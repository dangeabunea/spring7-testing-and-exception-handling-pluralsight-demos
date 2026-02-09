package com.pluralsight.afm.dto;

import com.pluralsight.afm.validation.ValidFlightRoute;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data transfer object for creating a new flight.
 */
@ValidFlightRoute
public record CreateFlightDto(

        @NotBlank(message = "Flight number is required")
        @Size(min = 3, max = 7, message = "Flight number must have between 3 and 7 characters")
        String flightNumber,

        @NotBlank(message = "Departure ICAO country code is required")
        @Size(min = 1, max = 2, message = "ICAO country code must be 1 or 2 characters")
        @Pattern(regexp = "^[A-Z]{1,2}$", message = "ICAO country code must contain only uppercase letters")
        String departureIcaoCountryCode,

        @NotBlank(message = "Departure city is required")
        String departureCity,

        @NotBlank(message = "Departure ICAO airport code is required")
        @Size(min = 4, max = 4, message = "ICAO airport code must be exactly 4 characters")
        @Pattern(regexp = "^[A-Z]{4}$", message = "ICAO airport code must contain only uppercase letters")
        String departureIcaoAirportCode,

        @NotBlank(message = "Destination ICAO country code is required")
        @Size(min = 1, max = 2, message = "ICAO country code must be 1 or 2 characters")
        @Pattern(regexp = "^[A-Z]{1,2}$", message = "ICAO country code must contain only uppercase letters")
        String destinationIcaoCountryCode,

        @NotBlank(message = "Destination city is required")
        String destinationCity,

        @NotBlank(message = "Destination ICAO airport code is required")
        @Size(min = 4, max = 4, message = "ICAO airport code must be exactly 4 characters")
        @Pattern(regexp = "^[A-Z]{4}$", message = "ICAO airport code must contain only uppercase letters")
        String destinationIcaoAirportCode,

        @NotNull(message = "Aircraft ID is required")
        UUID aircraftId,

        @NotNull(message = "Scheduled departure time is required")
        LocalDateTime scheduledDepartureTime,

        @Positive(message = "Duration must be a positive number of minutes")
        @Max(value = 720, message = "Duration cannot exceed 720 minutes (12 hours)")
        int durationInMinutes
) {
}
