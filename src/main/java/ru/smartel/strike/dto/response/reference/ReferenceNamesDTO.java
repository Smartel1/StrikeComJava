package ru.smartel.strike.dto.response.reference;

import ru.smartel.strike.dto.response.NamesExtendableDTO;
import ru.smartel.strike.entity.interfaces.NamedReference;
import ru.smartel.strike.service.Locale;

public class ReferenceNamesDTO extends NamesExtendableDTO {

    private long id;

    public static ReferenceNamesDTO of(NamedReference entity, Locale locale) {
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
