package ru.smartel.strike.dto.response;

import ru.smartel.strike.entity.interfaces.Titles;
import ru.smartel.strike.service.Locale;

/**
 * Класс-заготовка для DTO, которые содержат локализованные заголовки
 */
public abstract class TitlesExtendableDTO extends ExtendableDTO {

    public TitlesExtendableDTO(Titles entity, Locale locale) {
        if (locale == Locale.ALL) {
            add("title_ru", entity.getTitleRu());
            add("title_en", entity.getTitleEn());
            add("title_es", entity.getTitleEs());
        } else {
            add("title", entity.getTitleByLocale(locale));
        }
    }
}
