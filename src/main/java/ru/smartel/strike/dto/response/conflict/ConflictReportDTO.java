package ru.smartel.strike.dto.response.conflict;

import java.util.HashMap;
import java.util.Map;

public class ConflictReportDTO {
    private Map<String, Integer> countByCountries = new HashMap<>();
    private Map<String, Integer> countByDistricts = new HashMap<>();
    private Map<String, Float> specificCountByDistricts = new HashMap<>(); // per citizen
    private Map<String, Integer> countByIndustry = new HashMap<>();
    private Map<String, Integer> countByReason = new HashMap<>();
    private Map<String, Integer> countByResult = new HashMap<>();
    private Map<String, Integer> countPercentByIndustry = new HashMap<>();
    private Map<String, Integer> countPercentByReason = new HashMap<>();
    private Map<String, Integer> countPercentByResult = new HashMap<>();

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

    public Map<String, Integer> getCountByIndustry() {
        return countByIndustry;
    }

    public void setCountByIndustry(Map<String, Integer> countByIndustry) {
        this.countByIndustry = countByIndustry;
    }

    public Map<String, Integer> getCountByReason() {
        return countByReason;
    }

    public void setCountByReason(Map<String, Integer> countByReason) {
        this.countByReason = countByReason;
    }

    public Map<String, Integer> getCountByResult() {
        return countByResult;
    }

    public void setCountByResult(Map<String, Integer> countByResult) {
        this.countByResult = countByResult;
    }

    public Map<String, Integer> getCountPercentByIndustry() {
        return countPercentByIndustry;
    }

    public void setCountPercentByIndustry(Map<String, Integer> countPercentByIndustry) {
        this.countPercentByIndustry = countPercentByIndustry;
    }

    public Map<String, Integer> getCountPercentByReason() {
        return countPercentByReason;
    }

    public void setCountPercentByReason(Map<String, Integer> countPercentByReason) {
        this.countPercentByReason = countPercentByReason;
    }

    public Map<String, Integer> getCountPercentByResult() {
        return countPercentByResult;
    }

    public void setCountPercentByResult(Map<String, Integer> countPercentByResult) {
        this.countPercentByResult = countPercentByResult;
    }
}
