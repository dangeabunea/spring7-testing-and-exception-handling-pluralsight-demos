package com.pluralsight.afm.service;

import com.pluralsight.afm.domain.CountryFlightAlert;
import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.domain.Location;
import com.pluralsight.afm.dto.CreateFlightDto;
import com.pluralsight.afm.exception.FlightNotFoundException;
import com.pluralsight.afm.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Transactional
    public Flight createFlight(CreateFlightDto request) {
        Location departure = new Location(
                request.departureIcaoCountryCode(),
                request.departureCity(),
                request.departureIcaoAirportCode()
        );

        Location destination = new Location(
                request.destinationIcaoCountryCode(),
                request.destinationCity(),
                request.destinationIcaoAirportCode()
        );

        if (departure.getIcaoAirportCode().equals(destination.getIcaoAirportCode())) {
            throw new IllegalArgumentException("Departure and destination must be different");
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
                .findActiveAlertForCountryAt(departure.getIcaoCountryCode(), request.scheduledDepartureTime());

        Optional<CountryFlightAlert> destinationAlert = alertService
                .findActiveAlertForCountryAt(destination.getIcaoCountryCode(), request.scheduledDepartureTime());

        if (departureAlert.isPresent() || destinationAlert.isPresent()) {
            throw new IllegalStateException("Flight cannot be scheduled because of an active alert");
        }

        return flightRepository.save(flight);
    }

    public Flight cancelFlight(String flightNumber) {
        Flight flight = flightRepository
                .findByFlightNumber(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException(flightNumber));
        flight.cancel();

        return flightRepository.save(flight);
    }

    public Optional<Flight> findByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }
}
