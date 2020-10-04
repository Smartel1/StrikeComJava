package ru.smartel.strike.dto.response.conflict.report;

public class CountByDistrict {
    private Long districtId;
    private long count;

    public CountByDistrict(Long districtId, long count) {
        this.districtId = districtId;
        this.count = count;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
