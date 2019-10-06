package ru.smartel.strike.dto.response.conflict;

import ru.smartel.strike.dto.response.TitlesExtendableDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;

public class ConflictDetailDTO extends TitlesExtendableDTO {

    public ConflictDetailDTO (Conflict conflict, Locale locale) {
        super(conflict, locale);
        id = conflict.getId();
        latitude = conflict.getLatitude();
        longitude = conflict.getLongitude();
        companyName = conflict.getCompanyName();
        dateFrom = null != conflict.getDateFrom() ? conflict.getDateFrom().toEpochSecond(ZoneOffset.UTC) : null;
        dateTo = null != conflict.getDateTo() ? conflict.getDateTo().toEpochSecond(ZoneOffset.UTC) : null;
        conflictReasonId = null != conflict.getConflictReason() ? conflict.getConflictReason().getId() : null;
        conflictResultId = null != conflict.getConflictResult() ? conflict.getConflictResult().getId() : null;
        industryId = null != conflict.getIndustry() ? conflict.getIndustry().getId() : null;
        parentEventId = null != conflict.getParentEvent() ? conflict.getParentEvent().getId() : null;
    }

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