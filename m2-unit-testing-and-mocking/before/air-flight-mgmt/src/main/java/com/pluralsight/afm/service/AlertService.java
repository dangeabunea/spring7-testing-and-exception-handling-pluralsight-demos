package com.pluralsight.afm.service;

import com.pluralsight.afm.domain.CountryFlightAlert;
import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.FlightStatus;
import com.pluralsight.afm.repository.AlertRepository;
import com.pluralsight.afm.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing country flight alerts.
 * When an alert is declared, affected flights are automatically updated:
 * - If alert duration < 3 hours: flights are rescheduled to after the alert ends
 * - If alert duration >= 3 hours: flights are cancelled
 */
@Service
public class AlertService {

    private static final long CANCELLATION_THRESHOLD_HOURS = 3;

    private final AlertRepository alertRepository;
    private final FlightRepository flightRepository;

    public AlertService(AlertRepository alertRepository, FlightRepository flightRepository) {
        this.alertRepository = alertRepository;
        this.flightRepository = flightRepository;
    }

    public CountryFlightAlert declareAlert(String icaoCountryCode,
                                           LocalDateTime startDateTime,
                                           LocalDateTime endDateTime,
                                           String description) {
        CountryFlightAlert alert = new CountryFlightAlert(
                UUID.randomUUID(),
                icaoCountryCode,
                startDateTime,
                endDateTime,
                description);

        CountryFlightAlert savedAlert = alertRepository.save(alert);

        updateAffectedFlights(savedAlert);

        return savedAlert;
    }

    private void updateAffectedFlights(CountryFlightAlert alert) {
        List<Flight> affectedFlights = flightRepository.findAffectedFlights(
                alert.getIcaoCountryCode(),
                alert.getStartDateTime(),
                alert.getEndDateTime(),
                List.of(FlightStatus.SCHEDULED, FlightStatus.RESCHEDULED));

        for (Flight flight : affectedFlights) {
            if (alert.getDurationInHours() < CANCELLATION_THRESHOLD_HOURS) {
                flight.reschedule(alert.getEndDateTime().plusMinutes(30));
            } else {
                flight.cancel();
            }
            flight.setAffectedByAlertId(alert.getId());
            flightRepository.save(flight);
        }
    }

    public Optional<CountryFlightAlert> findActiveAlertForCountryAt(String icaoCountryCode,
                                                                    LocalDateTime dateTime) {
        return alertRepository.findByIcaoCountryCode(icaoCountryCode)
                .stream()
                .filter(alert -> alert.isActiveAt(dateTime))
                .findFirst();
    }

    public Optional<CountryFlightAlert> findById(UUID id) {
        return alertRepository.findById(id);
    }

    public List<CountryFlightAlert> findAll() {
        return alertRepository.findAll();
    }
}
