package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.Reference;

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
