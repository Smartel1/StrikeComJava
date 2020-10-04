package ru.smartel.strike.dto.response.conflict.report;

public class CountByResult {
    private Long resultId;
    private long count;

    public CountByResult(Long resultId, long count) {
        this.resultId = resultId;
        this.count = count;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
