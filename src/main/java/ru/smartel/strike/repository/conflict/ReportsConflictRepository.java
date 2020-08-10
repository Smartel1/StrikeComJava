package ru.smartel.strike.repository.conflict;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;

@Repository
public interface ReportsConflictRepository {
    Map<String, Integer> getCountByCountries(LocalDate from, LocalDate to);

    Map<String, Integer> getCountByDistricts(LocalDate from, LocalDate to);

    Map<String, Float> getSpecificCountByDistricts(LocalDate from, LocalDate to);

    Map<String, Integer> getCountByIndustry(LocalDate from, LocalDate to);

    Map<String, Integer> getCountByReason(LocalDate from, LocalDate to);

    Map<String, Integer> getCountByResult(LocalDate from, LocalDate to);
}
