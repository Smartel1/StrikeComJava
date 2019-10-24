package ru.smartel.strike.dto.response.news;

import ru.smartel.strike.dto.response.post.PostListDTO;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.service.Locale;

public class NewsListDTO extends PostListDTO {

    public NewsListDTO(News news, Locale locale) {
        super(news, locale);
    }
}
