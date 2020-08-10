package ru.smartel.strike.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_types")
public class EventType extends EntityWithNames {

    public EventType() {
    }

    public EventType(String nameRu, String nameEn, String nameEs, String nameDe) {
        super(nameRu, nameEn, nameEs, nameDe);
    }

    @Column(name = "priority")
    private Integer priority;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
