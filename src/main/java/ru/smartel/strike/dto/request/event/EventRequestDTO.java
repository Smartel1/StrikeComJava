package ru.smartel.strike.dto.request.event;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.dto.request.video.VideoDTO;

import java.util.List;
import java.util.Optional;

/**
 * dto for creating/updating requests
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRequestDTO {
    private Optional<Integer> conflictId;
    private Optional<Boolean> published;
    private Optional<String> title;
    private Optional<String> titleRu;
    private Optional<String> titleEn;
    private Optional<String> titleEs;
    private Optional<String> content;
    private Optional<String> contentRu;
    private Optional<String> contentEn;
    private Optional<String> contentEs;
    private Optional<Long> date;
    private Optional<Float> latitude;
    private Optional<Float> longitude;
    private Optional<String> sourceLink;
    private Optional<Integer> localityId;
    private Optional<Integer> eventStatusId;
    private Optional<Integer> eventTypeId;
    private List<String> tags;
    private List<String> photoUrls;
    private List<VideoDTO> videos;

    public Optional<Integer> getConflictId() {
        return conflictId;
    }

    public void setConflictId(Optional<Integer> conflictId) {
        this.conflictId = conflictId;
    }

    public Optional<Boolean> getPublished() {
        return published;
    }

    public void setPublished(Optional<Boolean> published) {
        this.published = published;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public void setTitle(Optional<String> title) {
        this.title = title;
    }

    public Optional<String> getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(Optional<String> titleRu) {
        this.titleRu = titleRu;
    }

    public Optional<String> getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(Optional<String> titleEn) {
        this.titleEn = titleEn;
    }

    public Optional<String> getTitleEs() {
        return titleEs;
    }

    public void setTitleEs(Optional<String> titleEs) {
        this.titleEs = titleEs;
    }

    public Optional<String> getContent() {
        return content;
    }

    public void setContent(Optional<String> content) {
        this.content = content;
    }

    public Optional<String> getContentRu() {
        return contentRu;
    }

    public void setContentRu(Optional<String> contentRu) {
        this.contentRu = contentRu;
    }

    public Optional<String> getContentEn() {
        return contentEn;
    }

    public void setContentEn(Optional<String> contentEn) {
        this.contentEn = contentEn;
    }

    public Optional<String> getContentEs() {
        return contentEs;
    }

    public void setContentEs(Optional<String> contentEs) {
        this.contentEs = contentEs;
    }

    public Optional<Long> getDate() {
        return date;
    }

    public void setDate(Optional<Long> date) {
        this.date = date;
    }

    public Optional<Float> getLatitude() {
        return latitude;
    }

    public void setLatitude(Optional<Float> latitude) {
        this.latitude = latitude;
    }

    public Optional<Float> getLongitude() {
        return longitude;
    }

    public void setLongitude(Optional<Float> longitude) {
        this.longitude = longitude;
    }

    public Optional<String> getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(Optional<String> sourceLink) {
        this.sourceLink = sourceLink;
    }

    public Optional<Integer> getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Optional<Integer> localityId) {
        this.localityId = localityId;
    }

    public Optional<Integer> getEventStatusId() {
        return eventStatusId;
    }

    public void setEventStatusId(Optional<Integer> eventStatusId) {
        this.eventStatusId = eventStatusId;
    }

    public Optional<Integer> getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Optional<Integer> eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<VideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }
}
