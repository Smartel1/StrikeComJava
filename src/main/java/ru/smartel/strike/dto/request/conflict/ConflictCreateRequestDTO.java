package ru.smartel.strike.dto.request.conflict;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.Locale;

import java.util.Optional;

/**
 * dto for creating conflict
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConflictCreateRequestDTO {
    @JsonIgnore
    private Locale locale;
    @JsonIgnore
    private User user;
    private Optional<String> title;
    private Optional<String> titleRu;
    private Optional<String> titleEn;
    private Optional<String> titleEs;
    private Float latitude;
    private Float longitude;
    private Optional<String> companyName;
    private Optional<Long> dateFrom;
    private Optional<Long> dateTo;
    private Optional<Integer> conflictReasonId;
    private Optional<Integer> conflictResultId;
    private Optional<Integer> industryId;
    private Optional<Integer> parentEventId;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public void setTitle(Optional<String> title) {
        this.title = title;
    }

    public Optional<String> getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(Optional<String> titleRu) {
        this.titleRu = titleRu;
    }

    public Optional<String> getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(Optional<String> titleEn) {
        this.titleEn = titleEn;
    }

    public Optional<String> getTitleEs() {
        return titleEs;
    }

    public void setTitleEs(Optional<String> titleEs) {
        this.titleEs = titleEs;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Optional<String> getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Optional<String> companyName) {
        this.companyName = companyName;
    }

    public Optional<Long> getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Optional<Long> dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Optional<Long> getDateTo() {
        return dateTo;
    }

    public void setDateTo(Optional<Long> dateTo) {
        this.dateTo = dateTo;
    }

    public Optional<Integer> getConflictReasonId() {
        return conflictReasonId;
    }

    public void setConflictReasonId(Optional<Integer> conflictReasonId) {
        this.conflictReasonId = conflictReasonId;
    }

    public Optional<Integer> getConflictResultId() {
        return conflictResultId;
    }

    public void setConflictResultId(Optional<Integer> conflictResultId) {
        this.conflictResultId = conflictResultId;
    }

    public Optional<Integer> getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Optional<Integer> industryId) {
        this.industryId = industryId;
    }

    public Optional<Integer> getParentEventId() {
        return parentEventId;
    }

    public void setParentEventId(Optional<Integer> parentEventId) {
        this.parentEventId = parentEventId;
    }
}
