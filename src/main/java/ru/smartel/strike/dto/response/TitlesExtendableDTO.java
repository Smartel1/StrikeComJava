package ru.smartel.strike.dto.response;

import ru.smartel.strike.entity.interfaces.HavingTitles;
import ru.smartel.strike.service.Locale;

/**
 * Класс-заготовка для DTO, которые содержат локализованные заголовки
 */
public abstract class TitlesExtendableDTO extends ExtendableDTO {

    public void setTitlesOf(HavingTitles entity, Locale locale) {
        if (locale == Locale.ALL) {
            add("titleRu", entity.getTitleRu());
            add("titleEn", entity.getTitleEn());
            add("titleEs", entity.getTitleEs());
            add("titleDe", entity.getTitleDe());
        } else {
            add("title", entity.getTitleByLocale(locale));
        }
    }
}
