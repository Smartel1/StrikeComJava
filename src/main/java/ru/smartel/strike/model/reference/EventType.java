package ru.smartel.strike.model.reference;

import ru.smartel.strike.model.interfaces.Reference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_types")
public class EventType extends EntityWithNames implements Reference {

    public EventType() {
    }

    public EventType(String nameRu, String nameEn, String nameEs) {
        super(nameRu, nameEn, nameEs);
    }
}
