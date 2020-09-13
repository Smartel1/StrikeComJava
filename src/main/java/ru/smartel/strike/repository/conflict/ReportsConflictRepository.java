package ru.smartel.strike.repository.conflict;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ReportsConflictRepository {
    /**
     * Conflicts count, that have events in specified period, but started before 'from' date
     */
    long getOldConflictsCount(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Integer> getCountByCountries(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Integer> getCountByDistricts(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Float> getSpecificCountByDistricts(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Integer> getCountByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Integer> getCountByReasons(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Integer> getCountByResults(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Integer> getCountByTypes(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Map<String, Float>> getCountPercentByResultsByTypes(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Map<String, Float>> getCountPercentByResultsByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Map<String, Float>> getCountPercentByTypesByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds);
}
