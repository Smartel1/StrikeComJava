package ru.smartel.strike.dto.request.news;


import ru.smartel.strike.service.Locale;

/**
 * dto for detail view request
 */
public class NewsShowDetailRequestDTO {
    private Locale locale;
    private int newsId;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
}
