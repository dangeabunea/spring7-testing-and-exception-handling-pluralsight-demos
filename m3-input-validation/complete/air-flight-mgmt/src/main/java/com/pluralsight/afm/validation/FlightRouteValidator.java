package com.pluralsight.afm.validation;

import com.pluralsight.afm.dto.CreateFlightDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for the @ValidFlightRoute annotation.
 * Ensures that departure and destination cities are different in a flight
 */
public class FlightRouteValidator implements ConstraintValidator<ValidFlightRoute, CreateFlightDto> {

    @Override
    public boolean isValid(CreateFlightDto dto, ConstraintValidatorContext context) {
        if (dto.departureCity() == null || dto.destinationCity() == null) {
            return true;
        }

        return !dto.departureCity().equalsIgnoreCase(dto.destinationCity());
    }
}
