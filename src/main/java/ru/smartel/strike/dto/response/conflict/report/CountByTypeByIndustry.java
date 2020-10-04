package ru.smartel.strike.dto.response.conflict.report;

import java.util.List;

public class CountByTypeByIndustry {
    private long industryId;
    private List<CountByType> countByType;

    public CountByTypeByIndustry(long industryId, List<CountByType> countByType) {
        this.industryId = industryId;
        this.countByType = countByType;
    }

    public long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(long industryId) {
        this.industryId = industryId;
    }

    public List<CountByType> getCountByType() {
        return countByType;
    }

    public void setCountByType(List<CountByType> countByType) {
        this.countByType = countByType;
    }
}
