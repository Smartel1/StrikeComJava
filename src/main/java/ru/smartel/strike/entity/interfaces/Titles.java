package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.service.Locale;

public interface Titles {

    String getTitleRu();

    void setTitleRu(String title_ru);

    String getTitleEn();

    void setTitleEn(String title_en);

    String getTitleEs();

    void setTitleEs(String title_es);

    default String getTitleByLocale(Locale locale) {
        switch (locale) {
            case RU:
                return getTitleRu();
            case EN:
                return getTitleEn();
            case ES:
                return getTitleEs();
            default:
                return "";
        }
    }

    ;
}
