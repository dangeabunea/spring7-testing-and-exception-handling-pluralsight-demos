package com.pluralsight.afm.domain;

/**
 * Represents a location with country, city, and airport information.
 * Uses ICAO standards:
 * - Country prefix: 1-2 characters (e.g., "K" for USA, "EG" for UK, "LF" for France, LR for Romania)
 * - Airport code: 4 characters (e.g., "KJFK", "EGLL", "LFPG")
 */
public record Location(String icaoCountryCode, String city, String icaoAirportCode) {

    public Location {
        if (icaoCountryCode == null || icaoCountryCode.isEmpty() || icaoCountryCode.length() > 2) {
            throw new IllegalArgumentException("ICAO country prefix must be 1-2 characters");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (icaoAirportCode == null || icaoAirportCode.length() != 4) {
            throw new IllegalArgumentException("ICAO airport code must be exactly 4 characters");
        }
    }
}
