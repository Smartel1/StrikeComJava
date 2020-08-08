package ru.smartel.strike.repository.conflict;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ReportsConflictRepository {
    Map<String, Integer> getCountByCountries(int year);

    Map<String, Integer> getCountByDistricts(int year);

    Map<String, Float> getSpecificCountByDistricts(int year);

    Map<String, Integer> getCountByIndustry(int year);

    Map<String, Integer> getCountByReason(int year);

    Map<String, Integer> getCountByResult(int year);
}
