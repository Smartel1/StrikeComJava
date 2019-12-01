package ru.smartel.strike.dto.response.news;

import ru.smartel.strike.dto.response.post.PostDetailDTO;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.Optional;

public class NewsDetailDTO extends PostDetailDTO {

    private Long createdAt;

    public static NewsDetailDTO of(News news, Locale locale) {
        NewsDetailDTO instance = new NewsDetailDTO();
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
