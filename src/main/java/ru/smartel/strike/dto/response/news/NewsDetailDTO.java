package ru.smartel.strike.dto.response.news;

import ru.smartel.strike.dto.response.post.PostDetailDTO;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.service.Locale;

public class NewsDetailDTO extends PostDetailDTO {

    public NewsDetailDTO(News news, Locale locale) {
        super(news, locale);
    }
}
