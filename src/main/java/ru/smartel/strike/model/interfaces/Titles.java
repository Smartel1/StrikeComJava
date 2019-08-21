package ru.smartel.strike.model.interfaces;

public interface Titles {

    String getTitleRu();

    void setTitleRu(String title_ru);

    String getTitleEn();

    void setTitleEn(String title_en);

    String getTitleEs();

    void setTitleEs(String title_es);

    default String getTitleByLocale(String locale) {
        switch (locale) {
            case "ru":
                return getTitleRu();
            case "en":
                return getTitleEn();
            case "es":
                return getTitleEs();
            default:
                return "";
        }
    }

    ;
}
