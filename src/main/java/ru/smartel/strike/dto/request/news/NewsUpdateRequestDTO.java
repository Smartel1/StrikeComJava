package ru.smartel.strike.dto.request.news;


/**
 * dto for updating news
 */
public class NewsUpdateRequestDTO extends NewsCreateRequestDTO {
    private int newsId;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
}
