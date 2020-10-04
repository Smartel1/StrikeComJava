package ru.smartel.strike.dto.response.conflict.report;

public class CountByReason {
    private Long reasonId;
    private long count;

    public CountByReason(Long reasonId, long count) {
        this.reasonId = reasonId;
        this.count = count;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
