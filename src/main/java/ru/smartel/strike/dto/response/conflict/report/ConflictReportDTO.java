package ru.smartel.strike.dto.response.conflict.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConflictReportDTO {
    private long conflictsBeganBeforeDateFromCount;
    private List<CountByCountry> countByCountries = new ArrayList<>();
    private Map<String, Integer> countByDistricts = new HashMap<>();
    private Map<String, Float> specificCountByDistricts = new HashMap<>(); // per citizen
    private List<CountByIndustry> countByIndustries = new ArrayList<>();
    private List<CountByReason> countByReasons = new ArrayList<>();
    private List<CountByResult> countByResults = new ArrayList<>();
    private List<CountByType> countByTypes = new ArrayList<>();
    private List<CountByResultsByType> countByResultsByTypes = new ArrayList<>();
    private List<CountByResultsByIndustry> countByResultsByIndustries = new ArrayList<>();
    private List<CountByTypeByIndustry> countByTypesByIndustries = new ArrayList<>();

    public long getConflictsBeganBeforeDateFromCount() {
        return conflictsBeganBeforeDateFromCount;
    }

    public void setConflictsBeganBeforeDateFromCount(long conflictsBeganBeforeDateFromCount) {
        this.conflictsBeganBeforeDateFromCount = conflictsBeganBeforeDateFromCount;
    }

    public List<CountByCountry> getCountByCountries() {
        return countByCountries;
    }

    public void setCountByCountries(List<CountByCountry> countByCountries) {
        this.countByCountries = countByCountries;
    }

    public Map<String, Integer> getCountByDistricts() {
        return countByDistricts;
    }

    public void setCountByDistricts(Map<String, Integer> countByDistricts) {
        this.countByDistricts = countByDistricts;
    }

    public Map<String, Float> getSpecificCountByDistricts() {
        return specificCountByDistricts;
    }

    public void setSpecificCountByDistricts(Map<String, Float> specificCountByDistricts) {
        this.specificCountByDistricts = specificCountByDistricts;
    }

    public List<CountByIndustry> getCountByIndustries() {
        return countByIndustries;
    }

    public void setCountByIndustries(List<CountByIndustry> countByIndustries) {
        this.countByIndustries = countByIndustries;
    }

    public List<CountByReason> getCountByReasons() {
        return countByReasons;
    }

    public void setCountByReasons(List<CountByReason> countByReasons) {
        this.countByReasons = countByReasons;
    }

    public List<CountByResult> getCountByResults() {
        return countByResults;
    }

    public void setCountByResults(List<CountByResult> countByResults) {
        this.countByResults = countByResults;
    }

    public List<CountByType> getCountByTypes() {
        return countByTypes;
    }

    public void setCountByTypes(List<CountByType> countByTypes) {
        this.countByTypes = countByTypes;
    }

    public List<CountByResultsByType> getCountByResultsByTypes() {
        return countByResultsByTypes;
    }

    public void setCountByResultsByTypes(List<CountByResultsByType> countByResultsByTypes) {
        this.countByResultsByTypes = countByResultsByTypes;
    }

    public List<CountByResultsByIndustry> getCountByResultsByIndustries() {
        return countByResultsByIndustries;
    }

    public void setCountByResultsByIndustries(List<CountByResultsByIndustry> countByResultsByIndustries) {
        this.countByResultsByIndustries = countByResultsByIndustries;
    }

    public List<CountByTypeByIndustry> getCountByTypesByIndustries() {
        return countByTypesByIndustries;
    }

    public void setCountByTypesByIndustries(List<CountByTypeByIndustry> countByTypesByIndustries) {
        this.countByTypesByIndustries = countByTypesByIndustries;
    }
}
