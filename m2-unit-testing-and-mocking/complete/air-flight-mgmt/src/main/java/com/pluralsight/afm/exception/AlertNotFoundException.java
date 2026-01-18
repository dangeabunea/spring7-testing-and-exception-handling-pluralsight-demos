package com.pluralsight.afm.exception;

import java.util.UUID;

/**
 * Exception thrown when an alert cannot be found.
 */
public class AlertNotFoundException extends RuntimeException {
    private final UUID alertId;

    public AlertNotFoundException(UUID alertId) {
        super("Alert not found with id: " + alertId);
        this.alertId = alertId;
    }

    public UUID getAlertId() {
        return alertId;
    }
}
