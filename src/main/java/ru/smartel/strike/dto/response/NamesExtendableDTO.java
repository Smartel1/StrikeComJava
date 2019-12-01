package ru.smartel.strike.dto.response;

import ru.smartel.strike.entity.interfaces.HavingNames;
import ru.smartel.strike.service.Locale;

/**
 * Abstract DTO for entities with optional names fields
 */
public abstract class NamesExtendableDTO extends ExtendableDTO {

    protected void setNamesOf(HavingNames entity, Locale locale) {
        if (locale == Locale.ALL) {
            add("nameRu", entity.getNameRu());
            add("nameEn", entity.getNameEn());
            add("nameEs", entity.getNameEs());
            add("nameDe", entity.getNameDe());
        } else {
            add("name", entity.getNameByLocale(locale));
        }
    }
}
