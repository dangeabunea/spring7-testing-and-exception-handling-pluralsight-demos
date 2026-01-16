package com.pluralsight.afm.dto;

import java.time.LocalDateTime;

/**
 * Data transfer object for declaring a new country flight alert.
 * If endDateTime is null, the alert is considered ongoing.
 */
public record DeclareAlertRequestDto(
        String icaoCountryCode,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String description
) {
}
