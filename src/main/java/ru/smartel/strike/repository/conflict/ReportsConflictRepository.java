package ru.smartel.strike.repository.conflict;

import org.springframework.stereotype.Repository;
import ru.smartel.strike.dto.response.conflict.report.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ReportsConflictRepository {
    /**
     * Conflicts count, that have events in specified period, but started before 'from' date
     */
    long getOldConflictsCount(LocalDate from, LocalDate to, List<Long> countriesIds);

    List<CountByCountry> getCountByCountries(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Integer> getCountByDistricts(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Integer> getCountByRegions(LocalDate from, LocalDate to, List<Long> countriesIds);

    Map<String, Float> getSpecificCountByDistricts(LocalDate from, LocalDate to, List<Long> countriesIds);

    List<CountByIndustry> getCountByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds);

    List<CountByReason> getCountByReasons(LocalDate from, LocalDate to, List<Long> countriesIds);

    List<CountByResult> getCountByResults(LocalDate from, LocalDate to, List<Long> countriesIds);

    List<CountByType> getCountByTypes(LocalDate from, LocalDate to, List<Long> countriesIds);

    List<CountByResultsByType> getCountByResultsByTypes(LocalDate from, LocalDate to, List<Long> countriesIds);

    List<CountByResultsByIndustry> getCountByResultsByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds);

    List<CountByReasonsByIndustry> getCountByReasonsByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds);

    List<CountByTypeByIndustry> getCountPercentByTypesByIndustries(LocalDate from, LocalDate to, List<Long> countriesIds);
}
