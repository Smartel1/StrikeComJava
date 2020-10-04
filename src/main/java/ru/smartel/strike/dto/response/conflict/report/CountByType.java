package ru.smartel.strike.dto.response.conflict.report;

public class CountByType {
    private Long typeId;
    private long count;

    public CountByType(Long typeId, long count) {
        this.typeId = typeId;
        this.count = count;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
