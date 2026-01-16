package com.pluralsight.afm.service;

import com.pluralsight.afm.domain.CountryFlightAlert;
import com.pluralsight.afm.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing country flight alerts.
 */
@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public CountryFlightAlert declareAlert(String icaoCountryCode,
                                           LocalDateTime startDateTime,
                                           String description) {
        CountryFlightAlert alert = new CountryFlightAlert(
                UUID.randomUUID(),
                icaoCountryCode,
                startDateTime,
                description);
        return alertRepository.save(alert);
    }

    public Optional<CountryFlightAlert> findActiveAlertForCountryAt(String icaoCountryCode,
                                                                    LocalDateTime dateTime) {
        return alertRepository.findByIcaoCountryCode(icaoCountryCode)
                .stream()
                .filter(alert -> alert.isActiveAt(dateTime))
                .findFirst();
    }
}
