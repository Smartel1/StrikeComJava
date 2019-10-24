package ru.smartel.strike.entity.interfaces;

import ru.smartel.strike.service.Locale;

public interface Contents {

    String getContentRu();

    void setContentRu(String contentRu);

    String getContentEn();

    void setContentEn(String contentEn);

    String getContentEs();

    void setContentEs(String contentEs);

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

    default void setContentByLocale(Locale locale, String content) {
        switch (locale) {
            case RU:
                setContentRu(content);
                break;
            case EN:
                setContentEn(content);
                break;
            case ES:
                setContentEs(content);
                break;
        }
    }
}
