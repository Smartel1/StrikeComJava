package ru.smartel.strike.dto.response.conflict.report;

import java.util.List;

public class CountByResultsByType {
    private long typeId;
    private List<CountByResult> countByResult;

    public CountByResultsByType(long typeId, List<CountByResult> countByResult) {
        this.typeId = typeId;
        this.countByResult = countByResult;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public List<CountByResult> getCountByResult() {
        return countByResult;
    }

    public void setCountByResult(List<CountByResult> countByResult) {
        this.countByResult = countByResult;
    }
}
