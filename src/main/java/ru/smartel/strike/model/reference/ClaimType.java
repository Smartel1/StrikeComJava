package ru.smartel.strike.model.reference;

import ru.smartel.strike.model.interfaces.Reference;

import javax.persistence.*;

@Entity
@Table(name = "claim_types")
public class ClaimType extends EntityWithNames implements Reference {

    public ClaimType(String nameRu, String nameEn, String nameEs) {
        super(nameRu, nameEn, nameEs);
    }

    public ClaimType() {
    }
}
