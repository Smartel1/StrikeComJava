package ru.smartel.strike.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="events")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="title_ru")
    private String titleRu;

    @Column(name="title_en")
    private String titleEn;

    @Column(name="title_es")
    private String titleEs;

    @Column(name="content_ru")
    private String contentRu;

    @Column(name="content_en")
    private String contentEn;

    @Column(name="content_es")
    private String contentEs;

    @Column(name="date")
    private BigInteger date;

    @Column(name="views")
    private Integer views;

    @Column(name="source_link")
    private String sourceLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conflict_id")
    private Conflict conflict;

    @Column(name="event_status_id")
    private String eventStatusId;

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

    public Integer getId() {
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

    public String getContentRu() {
        return contentRu;
    }

    public void setContentRu(String contentRu) {
        this.contentRu = contentRu;
    }

    public String getContentEn() {
        return contentEn;
    }

    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
    }

    public String getContentEs() {
        return contentEs;
    }

    public void setContentEs(String contentEs) {
        this.contentEs = contentEs;
    }

    public BigInteger getDate() {
        return date;
    }

    public void setDate(BigInteger date) {
        this.date = date;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public Conflict getConflict() {
        return conflict;
    }

    public void setConflict(Conflict conflict) {
        this.conflict = conflict;
    }

    public String getEventStatusId() {
        return eventStatusId;
    }

    public void setEventStatusId(String eventStatusId) {
        this.eventStatusId = eventStatusId;
    }
}
