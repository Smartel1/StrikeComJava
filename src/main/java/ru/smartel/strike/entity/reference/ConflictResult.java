package ru.smartel.strike.entity.reference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "conflict_results")
public class ConflictResult extends EntityWithNames {

    public ConflictResult() {
    }

    public ConflictResult(String nameRu, String nameEn, String nameEs, String nameDe) {
        super(nameRu, nameEn, nameEs, nameDe);
    }
}
