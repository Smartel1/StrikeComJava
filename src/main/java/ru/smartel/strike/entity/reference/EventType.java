package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.NamedReference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_types")
public class EventType extends EntityWithNames implements NamedReference {

    public EventType() {
    }

    public EventType(String nameRu, String nameEn, String nameEs, String nameDe) {
        super(nameRu, nameEn, nameEs, nameDe);
    }
}
