package ru.smartel.strike.model.interfaces;

public interface Names {


    String getNameRu();

    void setNameRu(String name_ru);

    String getNameEn();

    void setNameEn(String name_en);

    String getNameEs();

    void setNameEs(String name_es);

    String getNameByLocale(String locale);
}
