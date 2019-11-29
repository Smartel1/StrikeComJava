package ru.smartel.strike.dto.response.conflict;

import ru.smartel.strike.dto.response.TitlesExtendableDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.Optional;

public class ConflictDetailDTO extends TitlesExtendableDTO {

    private long id;
    private double latitude;
    private double longitude;
    private String companyName;
    private Long dateFrom;
    private Long dateTo;
    private Long conflictReasonId;
    private Long conflictResultId;
    private Long industryId;
    private Long parentEventId;
    private Long createdAt;

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
        instance.setCreatedAt(Optional.ofNullable(conflict.getCreatedAt())
                .map(d -> d.toEpochSecond(ZoneOffset.UTC))
                .orElse(null));
        return instance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Long getConflictReasonId() {
        return conflictReasonId;
    }

    public void setConflictReasonId(Long conflictReasonId) {
        this.conflictReasonId = conflictReasonId;
    }

    public Long getConflictResultId() {
        return conflictResultId;
    }

    public void setConflictResultId(Long conflictResultId) {
        this.conflictResultId = conflictResultId;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getParentEventId() {
        return parentEventId;
    }

    public void setParentEventId(Long parentEventId) {
        this.parentEventId = parentEventId;
    }
}