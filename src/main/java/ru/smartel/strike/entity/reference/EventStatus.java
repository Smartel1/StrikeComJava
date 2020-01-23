package ru.smartel.strike.entity.reference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_statuses")
public class EventStatus extends EntityWithNamesAndSlug {
    public static final String NEW = "new";
    public static final String INTERMEDIATE = "intermediate";
    public static final String FINAL = "final";

    public EventStatus() {
    }

    public EventStatus(String nameRu, String nameEn, String nameEs, String nameDe, String slug) {
        super(nameRu, nameEn, nameEs, nameDe, slug);
    }
}
