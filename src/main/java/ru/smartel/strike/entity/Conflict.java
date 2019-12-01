package ru.smartel.strike.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.exsio.nestedj.model.NestedNode;
import ru.smartel.strike.entity.interfaces.HavingTitles;
import ru.smartel.strike.entity.reference.ConflictReason;
import ru.smartel.strike.entity.reference.ConflictResult;
import ru.smartel.strike.entity.reference.Industry;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "conflicts")
public class Conflict implements NestedNode<Long>, HavingTitles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "lft", nullable = false)
    private Long treeLeft; //dont set default value. Let it fail if not set while saving entity

    @Column(name = "rgt", nullable = false)
    private Long treeRight; //dont set default value. Let it fail if not set while saving entity

    @Column(name = "lvl", nullable = false)
    private Long treeLevel; //dont set default value. Let it fail if not set while saving entity

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "title_es")
    private String titleEs;

    @Column(name = "title_de")
    private String titleDe;

    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @Size(max = 500)
    @Column(name = "company_name", length = 500)
    private String companyName;

    @Column(name = "date_from")
    private LocalDateTime dateFrom;

    @Column(name = "date_to")
    private LocalDateTime dateTo;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "conflict", fetch = FetchType.LAZY)
    private List<Event> events;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_event_id")
    private Event parentEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conflict_reason_id")
    private ConflictReason reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conflict_result_id")
    private ConflictResult result;

    @ManyToOne(fetch = FetchType.LAZY)
    private Industry industry;

    public Long getId() {
        return id;
    }

    @Override
    public Long getTreeLeft() {
        return treeLeft;
    }

    @Override
    public Long getTreeRight() {
        return treeRight;
    }

    @Override
    public Long getTreeLevel() {
        return treeLevel;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setTreeLeft(Long treeLeft) {
        this.treeLeft = treeLeft;
    }

    @Override
    public void setTreeRight(Long treeRight) {
        this.treeRight = treeRight;
    }

    @Override
    public void setTreeLevel(Long treeLevel) {
        this.treeLevel = treeLevel;
    }

    @Override
    public void setParentId(Long parent) {
        this.parentId = parent;
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

    public String getTitleDe() {
        return titleDe;
    }

    public void setTitleDe(String titleDe) {
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

    public ConflictReason getReason() {
        return reason;
    }

    public void setReason(ConflictReason reason) {
        this.reason = reason;
    }

    public ConflictResult getResult() {
        return result;
    }

    public void setResult(ConflictResult result) {
        this.result = result;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Event getParentEvent() {
        return parentEvent;
    }

    public void setParentEvent(Event parentEvent) {
        this.parentEvent = parentEvent;
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

    @Override
    public String toString() {
        return "Conflict{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
