package ru.smartel.strike.dto.response;

import lombok.Data;
import ru.smartel.strike.entity.interfaces.Names;
import ru.smartel.strike.service.Locale;

/**
 * Класс-заготовка для DTO, которые содержат локализованные наименования
 */
@Data
public abstract class NamesExtendableDTO extends ExtendableDTO {
    public NamesExtendableDTO(Names entity, Locale locale) {
        if (locale == Locale.ALL) {
            add("name_ru", entity.getNameRu());
            add("name_en", entity.getNameEn());
            add("name_es", entity.getNameEs());
        } else {
            add("name", entity.getNameByLocale(locale));
        }
    }
}
