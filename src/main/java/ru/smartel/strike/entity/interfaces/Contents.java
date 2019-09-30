package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.service.Locale;

public interface Contents {

    String getContentRu();

    void setContentRu(String title_ru);

    String getContentEn();

    void setContentEn(String title_en);

    String getContentEs();

    void setContentEs(String title_es);

    default String getContentByLocale(Locale locale) {
        switch (locale) {
            case RU:
                return getContentRu();
            case EN:
                return getContentEn();
            case ES:
                return getContentEs();
            default:
                return "";
        }
    }

    ;
}
