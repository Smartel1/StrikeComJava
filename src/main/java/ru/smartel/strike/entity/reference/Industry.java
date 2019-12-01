package ru.smartel.strike.entity.reference;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "industries")
public class Industry extends EntityWithNames {

    public Industry() {
    }

    public Industry(String nameRu, String nameEn, String nameEs, String nameDe) {
        super(nameRu, nameEn, nameEs, nameDe);
    }
}
