package ru.smartel.strike.dto.response.conflict;

import java.util.HashMap;
import java.util.Map;

public class ConflictReportDTO {
    private long conflictsBeganBeforeDateFromCount;
    private Map<String, Integer> countByCountries = new HashMap<>();
    private Map<String, Integer> countByDistricts = new HashMap<>();
    private Map<String, Float> specificCountByDistricts = new HashMap<>(); // per citizen
    private Map<String, Integer> countByIndustries = new HashMap<>();
    private Map<String, Integer> countByReasons = new HashMap<>();
    private Map<String, Integer> countByResults = new HashMap<>();
    private Map<String, Integer> countByTypes = new HashMap<>();
    private Map<String, Integer> countPercentByIndustries = new HashMap<>();
    private Map<String, Integer> countPercentByReasons = new HashMap<>();
    private Map<String, Integer> countPercentByResults = new HashMap<>();
    private Map<String, Integer> countPercentByTypes = new HashMap<>();
    private Map<String, Map<String, Float>> countPercentByResultsByTypes = new HashMap<>();
    private Map<String, Map<String, Float>> countPercentByResultsByIndustries = new HashMap<>();
    private Map<String, Map<String, Float>> countPercentByTypesByIndustries = new HashMap<>();

    public long getConflictsBeganBeforeDateFromCount() {
        return conflictsBeganBeforeDateFromCount;
    }

    public void setConflictsBeganBeforeDateFromCount(long conflictsBeganBeforeDateFromCount) {
        this.conflictsBeganBeforeDateFromCount = conflictsBeganBeforeDateFromCount;
    }

    public Map<String, Integer> getCountByCountries() {
        return countByCountries;
    }

    public void setCountByCountries(Map<String, Integer> countByCountries) {
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

    public Map<String, Integer> getCountByIndustries() {
        return countByIndustries;
    }

    public void setCountByIndustries(Map<String, Integer> countByIndustries) {
        this.countByIndustries = countByIndustries;
    }

    public Map<String, Integer> getCountByReasons() {
        return countByReasons;
    }

    public void setCountByReasons(Map<String, Integer> countByReasons) {
        this.countByReasons = countByReasons;
    }

    public Map<String, Integer> getCountByResults() {
        return countByResults;
    }

    public void setCountByResults(Map<String, Integer> countByResults) {
        this.countByResults = countByResults;
    }

    public Map<String, Integer> getCountByTypes() {
        return countByTypes;
    }

    public void setCountByTypes(Map<String, Integer> countByTypes) {
        this.countByTypes = countByTypes;
    }

    public Map<String, Integer> getCountPercentByIndustries() {
        return countPercentByIndustries;
    }

    public void setCountPercentByIndustries(Map<String, Integer> countPercentByIndustries) {
        this.countPercentByIndustries = countPercentByIndustries;
    }

    public Map<String, Integer> getCountPercentByReasons() {
        return countPercentByReasons;
    }

    public void setCountPercentByReasons(Map<String, Integer> countPercentByReasons) {
        this.countPercentByReasons = countPercentByReasons;
    }

    public Map<String, Integer> getCountPercentByResults() {
        return countPercentByResults;
    }

    public void setCountPercentByResults(Map<String, Integer> countPercentByResults) {
        this.countPercentByResults = countPercentByResults;
    }

    public Map<String, Integer> getCountPercentByTypes() {
        return countPercentByTypes;
    }

    public void setCountPercentByTypes(Map<String, Integer> countPercentByTypes) {
        this.countPercentByTypes = countPercentByTypes;
    }

    public Map<String, Map<String, Float>> getCountPercentByResultsByTypes() {
        return countPercentByResultsByTypes;
    }

    public void setCountPercentByResultsByTypes(Map<String, Map<String, Float>> countPercentByResultsByTypes) {
        this.countPercentByResultsByTypes = countPercentByResultsByTypes;
    }

    public Map<String, Map<String, Float>> getCountPercentByResultsByIndustries() {
        return countPercentByResultsByIndustries;
    }

    public void setCountPercentByResultsByIndustries(Map<String, Map<String, Float>> countPercentByResultsByIndustries) {
        this.countPercentByResultsByIndustries = countPercentByResultsByIndustries;
    }

    public Map<String, Map<String, Float>> getCountPercentByTypesByIndustries() {
        return countPercentByTypesByIndustries;
    }

    public void setCountPercentByTypesByIndustries(Map<String, Map<String, Float>> countPercentByTypesByIndustries) {
        this.countPercentByTypesByIndustries = countPercentByTypesByIndustries;
    }
}
