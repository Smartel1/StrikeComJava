package ru.smartel.strike.entity.reference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "conflict_reasons")
public class ConflictReason extends EntityWithNames {

    public ConflictReason() {
    }

    public ConflictReason(String nameRu, String nameEn, String nameEs, String nameDe) {
        super(nameRu, nameEn, nameEs, nameDe);
    }
}
