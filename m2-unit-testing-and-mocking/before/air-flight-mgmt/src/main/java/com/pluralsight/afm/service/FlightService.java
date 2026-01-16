package com.pluralsight.afm.service;

import com.pluralsight.afm.domain.CountryFlightAlert;
import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.Location;
import com.pluralsight.afm.dto.CreateFlightRequestDto;
import com.pluralsight.afm.exception.FlightNotFoundException;
import com.pluralsight.afm.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final AlertService alertService;

    public FlightService(FlightRepository flightRepository, AlertService alertService) {
        this.flightRepository = flightRepository;
        this.alertService = alertService;
    }

    /**
     * Creates a new flight from the request.
     * If there's an active alert for the departure or destination country,
     * the flight is automatically cancelled and linked to the alert.
     */
    public Flight createFlight(CreateFlightRequestDto request) {
        Location departure = new Location(
                request.departureIcaoCountryPrefix(),
                request.departureCity(),
                request.departureIcaoAirportCode()
        );

        Location destination = new Location(
                request.destinationIcaoCountryPrefix(),
                request.destinationCity(),
                request.destinationIcaoAirportCode()
        );

        if (departure.icaoAirportCode().equals(destination.icaoAirportCode())) {
            throw new IllegalArgumentException("Departure and destination airports must be different");
        }

        Flight flight = new Flight(
                UUID.randomUUID(),
                request.flightNumber(),
                departure,
                destination,
                request.aircraftId(),
                request.scheduledDepartureTime(),
                request.durationInMinutes()
        );

        // Check for active alerts affecting the flight
        Optional<CountryFlightAlert> departureAlert = alertService
                .findActiveAlertForCountryAt(departure.icaoCountryCode(), request.scheduledDepartureTime());

        Optional<CountryFlightAlert> destinationAlert = alertService
                .findActiveAlertForCountryAt(destination.icaoCountryCode(), request.scheduledDepartureTime());

        if (departureAlert.isPresent()) {
            flight.cancel();
            flight.setAffectedByAlertId(departureAlert.get().getId());
        } else if (destinationAlert.isPresent()) {
            flight.cancel();
            flight.setAffectedByAlertId(destinationAlert.get().getId());
        }

        return flightRepository.save(flight);
    }

    public Flight cancelFlight(UUID flightId) {
        Flight flight = flightRepository
                .findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(flightId.toString()));

        flight.cancel();

        return flightRepository.save(flight);
    }
}
