package ru.smartel.strike.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name="conflicts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conflict {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "title_es")
    private String titleEs;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Size(max = 500)
    @Column(name = "company_name", length = 500)
    private String companyName;

    @Column(name = "date_from")
    private BigInteger dateFrom;

    @Column(name = "date_to")
    private BigInteger dateTo;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "conflict", fetch = FetchType.LAZY)
    private List<Event> events;

    @ManyToOne
    @JoinColumn(name = "conflict_reason_id")
    private ConflictReason conflictReason;

    @ManyToOne
    @JoinColumn(name = "conflict_result_id")
    private ConflictResult conflictResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleEs() {
        return titleEs;
    }

    public void setTitleEs(String titleEs) {
        this.titleEs = titleEs;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigInteger getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(BigInteger dateFrom) {
        this.dateFrom = dateFrom;
    }

    public BigInteger getDateTo() {
        return dateTo;
    }

    public void setDateTo(BigInteger dateTo) {
        this.dateTo = dateTo;
    }

    public ConflictReason getConflictReason() {
        return conflictReason;
    }

    public void setConflictReason(ConflictReason conflictReason) {
        this.conflictReason = conflictReason;
    }

    public ConflictResult getConflictResult() {
        return conflictResult;
    }

    public void setConflictResult(ConflictResult conflictResult) {
        this.conflictResult = conflictResult;
    }
}
