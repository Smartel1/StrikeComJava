package ru.smartel.strike.dto.response.conflict.report;

public class CountByIndustry {
    private Long industryId;
    private long count;

    public CountByIndustry(Long industryId, long count) {
        this.industryId = industryId;
        this.count = count;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
