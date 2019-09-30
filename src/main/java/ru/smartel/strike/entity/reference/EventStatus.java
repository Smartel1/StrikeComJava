package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.Reference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_statuses")
public class EventStatus extends EntityWithNames implements Reference {

    public EventStatus() {
    }

    public EventStatus(String nameRu, String nameEn, String nameEs) {
        super(nameRu, nameEn, nameEs);
    }
}
