package ru.smartel.strike.dto.response;

import ru.smartel.strike.entity.interfaces.Names;
import ru.smartel.strike.service.Locale;

/**
 * Abstract DTO for entities with optional names fields
 */
public abstract class NamesExtendableDTO extends ExtendableDTO {

    protected void setNamesOf(Names entity, Locale locale) {
        if (locale == Locale.ALL) {
            add("nameRu", entity.getNameRu());
            add("nameEn", entity.getNameEn());
            add("nameEs", entity.getNameEs());
        } else {
            add("name", entity.getNameByLocale(locale));
        }
    }
}
