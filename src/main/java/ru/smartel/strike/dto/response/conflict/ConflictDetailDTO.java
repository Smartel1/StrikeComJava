package ru.smartel.strike.dto.response.conflict;

import ru.smartel.strike.dto.response.TitlesExtendableDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;

public class ConflictDetailDTO extends TitlesExtendableDTO {

    private int id;
    private double latitude;
    private double longitude;
    private String companyName;
    private Long dateFrom;
    private Long dateTo;
    private Integer conflictReasonId;
    private Integer conflictResultId;
    private Integer industryId;
    private Integer parentEventId;

    public static ConflictDetailDTO of(Conflict conflict, Locale locale) {
        ConflictDetailDTO instance = new ConflictDetailDTO();
        instance.setTitlesOf(conflict, locale);
        instance.setId(conflict.getId());
        instance.setLatitude(conflict.getLatitude());
        instance.setLongitude(conflict.getLongitude());
        instance.setCompanyName(conflict.getCompanyName());
        instance.setDateFrom(null != conflict.getDateFrom() ? conflict.getDateFrom().toEpochSecond(ZoneOffset.UTC) : null);
        instance.setDateTo(null != conflict.getDateTo() ? conflict.getDateTo().toEpochSecond(ZoneOffset.UTC) : null);
        instance.setConflictReasonId(null != conflict.getReason() ? conflict.getReason().getId() : null);
        instance.setConflictResultId(null != conflict.getResult() ? conflict.getResult().getId() : null);
        instance.setIndustryId(null != conflict.getIndustry() ? conflict.getIndustry().getId() : null);
        instance.setParentEventId(null != conflict.getParentEvent() ? conflict.getParentEvent().getId() : null);
        return instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Long getDateTo() {
        return dateTo;
    }

    public void setDateTo(Long dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getConflictReasonId() {
        return conflictReasonId;
    }

    public void setConflictReasonId(Integer conflictReasonId) {
        this.conflictReasonId = conflictReasonId;
    }

    public Integer getConflictResultId() {
        return conflictResultId;
    }

    public void setConflictResultId(Integer conflictResultId) {
        this.conflictResultId = conflictResultId;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public Integer getParentEventId() {
        return parentEventId;
    }

    public void setParentEventId(Integer parentEventId) {
        this.parentEventId = parentEventId;
    }
}