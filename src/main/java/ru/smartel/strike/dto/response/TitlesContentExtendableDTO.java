package ru.smartel.strike.dto.response;

import ru.smartel.strike.entity.interfaces.TitlesContents;
import ru.smartel.strike.service.Locale;

/**
 * Класс-заготовка для DTO, которые содержат локализованные заголовки и содержимое
 */
public abstract class TitlesContentExtendableDTO extends TitlesExtendableDTO {

    public TitlesContentExtendableDTO(TitlesContents entity, Locale locale) {
        super(entity, locale);
        if (locale == Locale.ALL) {
            add("content_ru", entity.getContentRu());
            add("content_en", entity.getContentEn());
            add("content_es", entity.getContentEs());
        } else {
            add("content", entity.getContentByLocale(locale));
        }
    }
}
