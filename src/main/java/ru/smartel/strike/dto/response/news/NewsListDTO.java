package ru.smartel.strike.dto.response.news;

import ru.smartel.strike.dto.response.post.PostListDTO;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.Optional;

public class NewsListDTO extends PostListDTO {

    private Long createdAt;

    public static NewsListDTO of(News news, Locale locale) {
        NewsListDTO instance = new NewsListDTO();
        instance.setCommonFieldsOf(news, locale);
        instance.setCreatedAt(Optional.ofNullable(news.getCreatedAt())
                .map(d -> d.toEpochSecond(ZoneOffset.UTC))
                .orElse(null));
        return instance;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
