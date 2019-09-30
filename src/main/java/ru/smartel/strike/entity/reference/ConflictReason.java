package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.Reference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "conflict_reasons")
public class ConflictReason extends EntityWithNames implements Reference {
    public ConflictReason() {
    }

    public ConflictReason(String nameRu, String nameEn, String nameEs) {
        super(nameRu, nameEn, nameEs);
    }
}
