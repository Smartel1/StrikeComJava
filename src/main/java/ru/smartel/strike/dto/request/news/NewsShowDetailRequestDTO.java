package ru.smartel.strike.dto.request.news;


import ru.smartel.strike.service.Locale;

/**
 * dto for detail view request
 */
public class NewsShowDetailRequestDTO {
    private Locale locale;
    private long newsId;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }
}
