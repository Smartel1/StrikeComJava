package ru.smartel.strike.dto.response.conflict;

import ru.smartel.strike.dto.response.TitlesExtendableDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.reference.EntityWithNames;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;

import static java.util.Optional.ofNullable;

public class ConflictDetailDTO extends TitlesExtendableDTO {

    private long id;
    private float latitude;
    private float longitude;
    private String companyName;
    private Long dateFrom;
    private Long dateTo;
    private Long conflictReasonId;
    private Long conflictResultId;
    private Long industryId;
    private Long parentEventId;
    private Long createdAt;
    private Long mainTypeId;
    private boolean automanagingMainType;

    public static ConflictDetailDTO of(Conflict conflict, Locale locale) {
        ConflictDetailDTO instance = new ConflictDetailDTO();
        instance.setTitlesOf(conflict, locale);
        instance.setId(conflict.getId());
        instance.setLatitude(conflict.getLatitude());
        instance.setLongitude(conflict.getLongitude());
        instance.setCompanyName(conflict.getCompanyName());
        instance.setDateFrom(ofNullable(conflict.getDateFrom()).map(df -> df.toEpochSecond(ZoneOffset.UTC)).orElse(null));
        instance.setDateTo(ofNullable(conflict.getDateTo()).map(df -> df.toEpochSecond(ZoneOffset.UTC)).orElse(null));
        instance.setConflictReasonId(ofNullable(conflict.getReason()).map(EntityWithNames::getId).orElse(null));
        instance.setConflictResultId(ofNullable(conflict.getResult()).map(EntityWithNames::getId).orElse(null));
        instance.setIndustryId(ofNullable(conflict.getIndustry()).map(EntityWithNames::getId).orElse(null));
        instance.setParentEventId(ofNullable(conflict.getParentEvent()).map(Event::getId).orElse(null));
        instance.setCreatedAt(ofNullable(conflict.getCreatedAt()).map(d -> d.toEpochSecond(ZoneOffset.UTC)).orElse(null));
        instance.setMainTypeId(ofNullable(conflict.getMainType()).map(EntityWithNames::getId).orElse(null));
        instance.setAutomanagingMainType(conflict.isAutomanagingMainType());
        return instance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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

    public Long getMainTypeId() {
        return mainTypeId;
    }

    public void setMainTypeId(Long mainTypeId) {
        this.mainTypeId = mainTypeId;
    }

    public boolean isAutomanagingMainType() {
        return automanagingMainType;
    }

    public void setAutomanagingMainType(boolean automanagingMainType) {
        this.automanagingMainType = automanagingMainType;
    }
}