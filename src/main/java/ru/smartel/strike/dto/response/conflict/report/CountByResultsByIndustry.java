package ru.smartel.strike.dto.response.conflict.report;

import java.util.List;

public class CountByResultsByIndustry {
    private long industryId;
    private List<CountByResult> countByResult;

    public CountByResultsByIndustry(long industryId, List<CountByResult> countByResult) {
        this.industryId = industryId;
        this.countByResult = countByResult;
    }

    public long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(long industryId) {
        this.industryId = industryId;
    }

    public List<CountByResult> getCountByResult() {
        return countByResult;
    }

    public void setCountByResult(List<CountByResult> countByResult) {
        this.countByResult = countByResult;
    }
}
