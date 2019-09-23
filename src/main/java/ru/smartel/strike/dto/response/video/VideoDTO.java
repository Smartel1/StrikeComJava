package ru.smartel.strike.dto.response.video;

import lombok.Data;
import ru.smartel.strike.model.Video;

@Data
public class VideoDTO {

    public VideoDTO(Video video) {
        url = video.getUrl();
        previewUrl = video.getPreviewUrl();
        videoTypeId = video.getVideoType().getId();
    }

    private String url;
    private String previewUrl;
    private int videoTypeId;
}
