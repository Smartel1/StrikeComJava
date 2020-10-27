package ru.smartel.strike.dto.response.conflict.report;

import java.util.List;

public class CountByReasonsByIndustry {
    private long industryId;
    private List<CountByReason> countByReason;

    public CountByReasonsByIndustry(long industryId, List<CountByReason> countByReason) {
        this.industryId = industryId;
        this.countByReason = countByReason;
    }

    public long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(long industryId) {
        this.industryId = industryId;
    }

    public List<CountByReason> getCountByReason() {
        return countByReason;
    }

    public void setCountByReason(List<CountByReason> countByReason) {
        this.countByReason = countByReason;
    }
}
