package com.pluralsight.afm.controller;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.dto.CreateFlightRequestDto;
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
    public ResponseEntity<Flight> createFlight(@RequestBody CreateFlightRequestDto request) {
        Flight flight = flightService.createFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(flight);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable UUID id) {
        return flightService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new FlightNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Flight> cancelFlight(@PathVariable UUID id) {
        Flight cancelled = flightService.cancelFlight(id);
        return ResponseEntity.ok(cancelled);
    }
}
