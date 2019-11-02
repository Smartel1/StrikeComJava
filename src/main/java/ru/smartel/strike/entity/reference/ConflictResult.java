package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.NamedReference;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "conflict_results")
public class ConflictResult extends EntityWithNames implements NamedReference {

    public ConflictResult() {
    }

    public ConflictResult(String nameRu, String nameEn, String nameEs) {
        super(nameRu, nameEn, nameEs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameRu, nameEn, nameEs);
    }
}
