package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.service.Locale;

public interface Names {

    String getNameRu();

    void setNameRu(String nameRu);

    String getNameEn();

    void setNameEn(String nameEn);

    String getNameEs();

    void setNameEs(String nameEs);

    String getNameDe();

    void setNameDe(String nameDe);

    String getNameByLocale(Locale locale);
}
