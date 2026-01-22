package com.pluralsight.afm.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CountryFlightAlertTest {

    @Nested
    class IsActiveAt {
        // Scenario 1 - Testing alert activation within time range
        @Test
        void should_return_true_when_value_is_in_alert_time_range() {
            // Arrange
            var alert = new CountryFlightAlert(
                    UUID.randomUUID(),
                    "LF",
                    LocalDateTime.of(2026, 1, 21, 10, 30),
                    LocalDateTime.of(2026, 1, 21, 12, 30),
                    "Test alert");

            // Act
            var result = alert.isActiveAt(LocalDateTime.of(2026, 1, 21, 11, 30));

            // Assert
            assertThat(result).isTrue();
        }

        // Scenario 2 - Testing alert activation before time range
        @Test
        void should_return_false_when_value_is_before_alert_start_time() {
            // Arrange
            var alert = new CountryFlightAlert(
                    UUID.randomUUID(),
                    "LF",
                    LocalDateTime.of(2026, 1, 21, 10, 30),
                    LocalDateTime.of(2026, 1, 21, 12, 30),
                    "Test alert");

            // Act
            var result = alert.isActiveAt(LocalDateTime.of(2026, 1, 21, 10, 29));

            // Assert
            assertThat(result).isFalse();
        }

        // Scenario 3 - Testing alert activation after time range
        @Test
        void should_return_false_when_value_is_after_alert_end_time() {
            // Arrange
            var alert = new CountryFlightAlert(
                    UUID.randomUUID(),
                    "LF",
                    LocalDateTime.of(2026, 1, 21, 10, 30),
                    LocalDateTime.of(2026, 1, 21, 12, 30),
                    "Test alert");

            // Act
            var result = alert.isActiveAt(LocalDateTime.of(2026, 1, 21, 12, 31));

            // Assert
            assertThat(result).isFalse();
        }
    }
}