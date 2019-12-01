package ru.smartel.strike.dto.response.reference;

import ru.smartel.strike.dto.response.NamesExtendableDTO;
import ru.smartel.strike.entity.reference.EntityWithNames;
import ru.smartel.strike.service.Locale;

public class ReferenceNamesDTO extends NamesExtendableDTO {

    private long id;

    public static ReferenceNamesDTO of(EntityWithNames entity, Locale locale) {
        ReferenceNamesDTO instance = new ReferenceNamesDTO();
        instance.setNamesOf(entity, locale);
        instance.setId(entity.getId());
        return instance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
