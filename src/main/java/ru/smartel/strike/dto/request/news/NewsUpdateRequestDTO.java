package ru.smartel.strike.dto.request.news;


/**
 * dto for updating news
 */
public class NewsUpdateRequestDTO extends NewsCreateRequestDTO {
    private Integer newsId;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }
}
