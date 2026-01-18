package com.pluralsight.afm.controller;

import com.pluralsight.afm.domain.CountryFlightAlert;
import com.pluralsight.afm.dto.DeclareAlertDto;
import com.pluralsight.afm.exception.AlertNotFoundException;
import com.pluralsight.afm.service.AlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public ResponseEntity<CountryFlightAlert> declareAlert(@RequestBody DeclareAlertDto request) {
        CountryFlightAlert alert = alertService.declareAlert(
                request.icaoCountryCode(),
                request.startDateTime(),
                request.endDateTime(),
                request.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(alert);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryFlightAlert> getAlert(@PathVariable UUID id) {
        return alertService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AlertNotFoundException(id));
    }

    @GetMapping
    public ResponseEntity<List<CountryFlightAlert>> getAllAlerts() {
        return ResponseEntity.ok(alertService.findAll());
    }
}
