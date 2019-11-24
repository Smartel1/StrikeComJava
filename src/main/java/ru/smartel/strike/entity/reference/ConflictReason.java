package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.NamedReference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "conflict_reasons")
public class ConflictReason extends EntityWithNames implements NamedReference {

    public ConflictReason() {
    }

    public ConflictReason(String nameRu, String nameEn, String nameEs, String nameDe) {
        super(nameRu, nameEn, nameEs, nameDe);
    }
}
