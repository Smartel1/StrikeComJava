package ru.smartel.strike.dto.response.video;

import ru.smartel.strike.entity.Video;

public class VideoDTO {

    private String url;
    private String previewUrl;
    private int videoTypeId;

    public VideoDTO(Video video) {
        url = video.getUrl();
        previewUrl = video.getPreviewUrl();
        videoTypeId = video.getVideoType().getId();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public int getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(int videoTypeId) {
        this.videoTypeId = videoTypeId;
    }
}
