package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.service.Locale;

public interface HavingNames {

    String getNameRu();

    void setNameRu(String nameRu);

    String getNameEn();

    void setNameEn(String nameEn);

    String getNameEs();

    void setNameEs(String nameEs);

    String getNameDe();

    void setNameDe(String nameDe);

    default String getNameByLocale(Locale locale) {
        switch (locale) {
            case RU:
                return getNameRu();
            case EN:
                return getNameEn();
            case ES:
                return getNameEs();
            case DE:
                return getNameDe();
            default:
                return "";
        }
    }
}
