package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.Reference;

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
