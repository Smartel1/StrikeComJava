package ru.smartel.strike.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_statuses")
public class EventStatus extends EntityWithNames {

    public static final String NEW = "new";
    public static final String INTERMEDIATE = "intermediate";
    public static final String FINAL = "final";

    public EventStatus() {
    }

    public EventStatus(String nameRu, String nameEn, String nameEs, String nameDe) {
        super(nameRu, nameEn, nameEs, nameDe);
    }

    @Column(name = "slug")
    private String slug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
