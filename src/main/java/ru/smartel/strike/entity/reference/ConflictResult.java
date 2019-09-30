package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.Reference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "conflict_results")
public class ConflictResult extends EntityWithNames implements Reference {
    public ConflictResult() {
    }

    public ConflictResult(String nameRu, String nameEn, String nameEs) {
        super(nameRu, nameEn, nameEs);
    }
}
