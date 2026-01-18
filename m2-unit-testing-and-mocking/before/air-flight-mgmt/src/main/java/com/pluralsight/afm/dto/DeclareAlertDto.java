package com.pluralsight.afm.dto;

import java.time.LocalDateTime;

public record DeclareAlertDto(
        String icaoCountryCode,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String description
) {
}
