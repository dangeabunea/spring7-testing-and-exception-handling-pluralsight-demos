package com.pluralsight.afm.repository;

import com.pluralsight.afm.domain.CountryFlightAlert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlertRepository {

    CountryFlightAlert save(CountryFlightAlert alert);

    Optional<CountryFlightAlert> findById(UUID id);

    List<CountryFlightAlert> findAll();

    List<CountryFlightAlert> findByIcaoCountryCode(String icaoCountryCode);

    void deleteById(Long id);
}
