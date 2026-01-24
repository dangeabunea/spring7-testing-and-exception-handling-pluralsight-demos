package com.pluralsight.afm.repository;

import com.pluralsight.afm.domain.CountryFlightAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlertRepository extends JpaRepository<CountryFlightAlert, UUID> {

    List<CountryFlightAlert> findByIcaoCountryCode(String icaoCountryCode);
}
