package com.pluralsight.afm.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Represents a location with country, city, and airport information.
 * Uses ICAO standards:
 * - Country prefix: 1-2 characters (e.g., "K" for USA, "EG" for UK, "LF" for France, LR for Romania)
 * - Airport code: 4 characters (e.g., "KJFK", "EGLL", "LFPG")
 */
@Embeddable
public class Location {
    private String icaoCountryCode;
    private String city;
    private String icaoAirportCode;

    protected Location() {}

    public Location(String icaoCountryCode, String city, String icaoAirportCode) {
        if (icaoCountryCode == null || icaoCountryCode.isEmpty() || icaoCountryCode.length() > 2) {
            throw new IllegalArgumentException("ICAO country prefix must be 1-2 characters");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (icaoAirportCode == null || icaoAirportCode.length() != 4) {
            throw new IllegalArgumentException("ICAO airport code must be exactly 4 characters");
        }

        this.icaoCountryCode = icaoCountryCode;
        this.city = city;
        this.icaoAirportCode = icaoAirportCode;
    }

    public String getIcaoCountryCode() {
        return icaoCountryCode;
    }

    public String getCity() {
        return city;
    }

    public String getIcaoAirportCode() {
        return icaoAirportCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(icaoCountryCode, location.icaoCountryCode) &&
               Objects.equals(city, location.city) &&
               Objects.equals(icaoAirportCode, location.icaoAirportCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icaoCountryCode, city, icaoAirportCode);
    }
}
