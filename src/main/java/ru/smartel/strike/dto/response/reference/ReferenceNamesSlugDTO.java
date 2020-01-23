package ru.smartel.strike.dto.response.reference;

import ru.smartel.strike.dto.response.NamesExtendableDTO;
import ru.smartel.strike.entity.reference.EntityWithNamesAndSlug;
import ru.smartel.strike.service.Locale;

public class ReferenceNamesSlugDTO extends NamesExtendableDTO {

    private long id;
    private String slug;

    public static ReferenceNamesSlugDTO of(EntityWithNamesAndSlug entity, Locale locale) {
        ReferenceNamesSlugDTO instance = new ReferenceNamesSlugDTO();
        instance.setNamesOf(entity, locale);
        instance.setId(entity.getId());
        instance.setSlug(entity.getSlug());
        return instance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
