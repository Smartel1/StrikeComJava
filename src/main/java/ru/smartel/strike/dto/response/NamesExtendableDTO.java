package ru.smartel.strike.dto.response;

import ru.smartel.strike.entity.interfaces.HavingNames;
import ru.smartel.strike.service.Locale;

import java.util.Comparator;
import java.util.List;

/**
 * Abstract DTO for entities with optional names fields
 */
public abstract class NamesExtendableDTO extends ExtendableDTO {
    public static final List<String> NAME_FIELDS_TO_COMPARE = List.of("name", "nameRu", "nameEn", "nameEs", "nameDe");

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

    /**
     * Сортировщик по имени (по первому заполненному)
     */
    public static Comparator<NamesExtendableDTO> byName() {
        return (c1, c2) -> {
            for (String nameToCompareBy : NAME_FIELDS_TO_COMPARE) {
                var name1 = (String) c1.getOptionalFields().get(nameToCompareBy);
                var name2 = (String) c2.getOptionalFields().get(nameToCompareBy);
                if (name1 != null && name2 != null) {
                    return name1.compareTo(name2);
                }
            }
            return 0;
        };
    }
}
