package ru.smartel.strike.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.smartel.strike.model.interfaces.Titles;
import ru.smartel.strike.model.reference.ConflictReason;
import ru.smartel.strike.model.reference.ConflictResult;
import ru.smartel.strike.model.reference.Industry;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "conflicts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conflict implements Titles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
    private LocalDateTime dateFrom;

    @Column(name = "date_to")
    private LocalDateTime dateTo;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "conflict", fetch = FetchType.LAZY)
    private List<Event> events;

    @ManyToOne
    @JoinColumn(name = "conflict_reason_id")
    private ConflictReason conflictReason;

    @ManyToOne
    @JoinColumn(name = "conflict_result_id")
    private ConflictResult conflictResult;

    @ManyToOne(targetEntity = Industry.class)
    private Industry industry;

    public int getId() {
        return id;
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

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
