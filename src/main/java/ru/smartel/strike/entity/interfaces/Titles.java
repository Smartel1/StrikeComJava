package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.service.Locale;

public interface Titles {

    String getTitleRu();

    void setTitleRu(String titleRu);

    String getTitleEn();

    void setTitleEn(String titleEn);

    String getTitleEs();

    void setTitleEs(String titleEs);

    String getTitleDe();

    void setTitleDe(String titleDe);

    default String getTitleByLocale(Locale locale) {
        switch (locale) {
            case RU:
                return getTitleRu();
            case EN:
                return getTitleEn();
            case ES:
                return getTitleEs();
            case DE:
                return getTitleDe();
            default:
                return "";
        }
    }

    default void setTitleByLocale(Locale locale, String title) {
        switch (locale) {
            case RU:
                setTitleRu(title);
                break;
            case EN:
                setTitleEn(title);
                break;
            case ES:
                setTitleEs(title);
                break;
            case DE:
                setTitleDe(title);
                break;
        }
    }
}
