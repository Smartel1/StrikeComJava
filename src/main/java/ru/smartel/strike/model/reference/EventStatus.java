package ru.smartel.strike.model.reference;

import ru.smartel.strike.model.interfaces.Reference;

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
