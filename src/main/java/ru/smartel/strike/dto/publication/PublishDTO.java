package ru.smartel.strike.dto.publication;

import java.util.List;

public class PublishDTO {
    private String text;
    private String sourceUrl;
    private List<String> videoUrls;

    public PublishDTO(String text, String sourceUrl, List<String> videoUrls) {
        this.text = text;
        this.sourceUrl = sourceUrl;
        this.videoUrls = videoUrls;
    }

    public String getText() {
        return text;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }
}
