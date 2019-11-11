package ru.smartel.strike.dto.request.news;


/**
 * dto for updating news
 */
public class NewsUpdateRequestDTO extends NewsCreateRequestDTO {
    private Long newsId;

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
}
