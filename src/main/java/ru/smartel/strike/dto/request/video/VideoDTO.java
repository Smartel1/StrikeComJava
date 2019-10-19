package ru.smartel.strike.dto.request.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDTO {
    private String url;
    private Optional<String> previewUrl;
    private Integer videoTypeId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Optional<String> getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(Optional<String> previewUrl) {
        this.previewUrl = previewUrl;
    }

    public Integer getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(Integer videoTypeId) {
        this.videoTypeId = videoTypeId;
    }
}
