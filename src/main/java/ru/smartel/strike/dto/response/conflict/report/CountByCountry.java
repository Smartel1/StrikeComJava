package ru.smartel.strike.dto.response.conflict.report;

public class CountByCountry {
    private Long countryId;
    private long count;

    public CountByCountry(Long countryId, long count) {
        this.countryId = countryId;
        this.count = count;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
