package ru.smartel.strike.dto.response.video;

import ru.smartel.strike.entity.Video;

public class VideoDTO {

    private String url;
    private String previewUrl;
    private int videoTypeId;

    public static VideoDTO from(Video video) {
        VideoDTO instance = new VideoDTO();
        instance.setUrl(video.getUrl());
        instance.setPreviewUrl(video.getPreviewUrl());
        instance.setVideoTypeId(video.getVideoType().getId());
        return instance;
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
