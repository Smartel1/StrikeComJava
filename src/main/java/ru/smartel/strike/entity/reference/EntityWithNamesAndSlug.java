package ru.smartel.strike.entity.reference;

import ru.smartel.strike.entity.interfaces.HavingSlug;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityWithNamesAndSlug extends EntityWithNames implements HavingSlug {

    @Column(name = "slug")
    private String slug;

    public EntityWithNamesAndSlug() {
    }

    public EntityWithNamesAndSlug(String nameRu, String nameEn, String nameEs, String nameDe, String slug) {
        super(nameRu, nameEn, nameEs, nameDe);
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
