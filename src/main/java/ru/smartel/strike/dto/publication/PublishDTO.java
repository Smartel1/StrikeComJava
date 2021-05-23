package ru.smartel.strike.dto.publication;

import java.util.List;

public class PublishDTO {
    private final String text;
    private final String sourceUrl;
    private final String sitePageUrl;
    private final List<String> videoUrls;

    public PublishDTO(String text, String sourceUrl, String sitePageUrl, List<String> videoUrls) {
        this.text = text;
        this.sourceUrl = sourceUrl;
        this.sitePageUrl = sitePageUrl;
        this.videoUrls = videoUrls;
    }

    public String getText() {
        return text;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getSitePageUrl() {
        return sitePageUrl;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }
}
