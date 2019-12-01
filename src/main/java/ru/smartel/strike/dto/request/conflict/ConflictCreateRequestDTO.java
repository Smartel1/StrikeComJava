package ru.smartel.strike.dto.request.conflict;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.security.token.UserPrincipal;
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
    private UserPrincipal user;
    private Optional<String> title;
    private Optional<String> titleRu;
    private Optional<String> titleEn;
    private Optional<String> titleEs;
    private Optional<String> titleDe;
    private Float latitude;
    private Float longitude;
    private Optional<String> companyName;
    private Optional<Long> dateFrom;
    private Optional<Long> dateTo;
    private Optional<Long> conflictReasonId;
    private Optional<Long> conflictResultId;
    private Optional<Long> industryId;
    private Optional<Long> parentEventId;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public UserPrincipal getUser() {
        return user;
    }

    public void setUser(UserPrincipal user) {
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

    public Optional<String> getTitleDe() {
        return titleDe;
    }

    public void setTitleDe(Optional<String> titleDe) {
        this.titleDe = titleDe;
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

    public Optional<Long> getConflictReasonId() {
        return conflictReasonId;
    }

    public void setConflictReasonId(Optional<Long> conflictReasonId) {
        this.conflictReasonId = conflictReasonId;
    }

    public Optional<Long> getConflictResultId() {
        return conflictResultId;
    }

    public void setConflictResultId(Optional<Long> conflictResultId) {
        this.conflictResultId = conflictResultId;
    }

    public Optional<Long> getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Optional<Long> industryId) {
        this.industryId = industryId;
    }

    public Optional<Long> getParentEventId() {
        return parentEventId;
    }

    public void setParentEventId(Optional<Long> parentEventId) {
        this.parentEventId = parentEventId;
    }
}
