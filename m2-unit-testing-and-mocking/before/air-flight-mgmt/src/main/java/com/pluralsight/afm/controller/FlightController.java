package com.pluralsight.afm.controller;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.dto.CreateFlightDto;
import com.pluralsight.afm.exception.FlightNotFoundException;
import com.pluralsight.afm.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody CreateFlightDto request) {
        Flight flight = flightService.createFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(flight);
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<Flight> getFlight(@PathVariable String flightNumber) {
        return flightService.findByFlightNumber(flightNumber)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new FlightNotFoundException(flightNumber));
    }

    @DeleteMapping("/{flightNumber}")
    public ResponseEntity<Flight> cancelFlight(@PathVariable String flightNumber) {
        Flight cancelled = flightService.cancelFlight(flightNumber);
        return ResponseEntity.ok(cancelled);
    }
}
