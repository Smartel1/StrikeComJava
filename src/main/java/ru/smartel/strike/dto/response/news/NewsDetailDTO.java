package ru.smartel.strike.dto.response.news;

import ru.smartel.strike.dto.response.post.PostDetailDTO;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.service.Locale;

public class NewsDetailDTO extends PostDetailDTO {

    public static NewsDetailDTO of(News news, Locale locale) {
        NewsDetailDTO instance = new NewsDetailDTO();
        instance.setCommonFieldsOf(news, locale);
        return instance;
    }
}
