package ru.smartel.strike.dto.response.reference;

import ru.smartel.strike.dto.response.NamesExtendableDTO;
import ru.smartel.strike.entity.interfaces.NamedReference;
import ru.smartel.strike.service.Locale;

public class ReferenceNamesDTO extends NamesExtendableDTO {

    private int id;

    public ReferenceNamesDTO(NamedReference entity, Locale locale) {
        super(entity, locale);
        id = entity.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
