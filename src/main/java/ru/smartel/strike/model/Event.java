package ru.smartel.strike.model;

import com.fasterxml.jackson.annotation.JsonView;
import ru.smartel.strike.jsonView.View;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="events")
public class Event {
    @Id
    @Column(name="id")
    @JsonView(View.List.class)
    private int id;

    @Column(name="title_ru")
    @JsonView(View.MultiLanguage.class)
    private String titleRu;

    @Column(name="title_en")
    @JsonView(View.MultiLanguage.class)
    private String titleEn;

    @Column(name="title_es")
    @JsonView(View.MultiLanguage.class)
    private String titleEs;

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

    @JsonView(View.List.class)
    public String getTitle() {
        return titleRu;
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
}
