package com.pluralsight.afm.controller;

import com.pluralsight.afm.domain.Flight;
import com.pluralsight.afm.dto.CreateFlightDto;
import com.pluralsight.afm.exception.FlightNotFoundException;
import com.pluralsight.afm.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createFlight(@Valid @RequestBody CreateFlightDto request,
                                          BindingResult bindingResult) {
        // Check for user input errors
        if (bindingResult.hasErrors()) {
            Map<String, List<String>> errors = new HashMap<>();
            // Build list of field-level errors
            bindingResult.getFieldErrors()
                    .forEach(error ->
                            errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
                                    .add(error.getDefaultMessage())
                    );
            // Build list of class-level errors
            bindingResult.getGlobalErrors()
                    .forEach(error ->
                            errors.computeIfAbsent(error.getObjectName(), key -> new ArrayList<>())
                                    .add(error.getDefaultMessage())
                    );
            return ResponseEntity.badRequest().body(errors);
        }

        Flight flight = flightService.createFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(flight);
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<Flight> getFlight(@PathVariable String flightNumber) {
        return flightService.findByFlightNumber(flightNumber)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new FlightNotFoundException(flightNumber));
    }

    @PostMapping("/{flightNumber}/cancel")
    public ResponseEntity<Flight> cancelFlight(@PathVariable String flightNumber) {
        Flight cancelled = flightService.cancelFlight(flightNumber);
        return ResponseEntity.ok(cancelled);
    }
}

