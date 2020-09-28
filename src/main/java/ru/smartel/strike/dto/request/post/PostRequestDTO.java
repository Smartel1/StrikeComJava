package ru.smartel.strike.dto.request.post;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.dto.request.video.VideoDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * dto for creating/updating requests
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class PostRequestDTO {
    private Optional<Boolean> published;
    private Optional<String> title;
    private Optional<String> titleRu;
    private Optional<String> titleEn;
    private Optional<String> titleEs;
    private Optional<String> titleDe;
    private Optional<String> content;
    private Optional<String> contentRu;
    private Optional<String> contentEn;
    private Optional<String> contentEs;
    private Optional<String> contentDe;
    private Optional<Long> date;
    private Optional<String> sourceLink;
    private Optional<List<String>> tags;
    private Optional<List<String>> photoUrls;
    private Optional<List<VideoDTO>> videos;
    private Set<Long> publishTo = Collections.emptySet();
    private boolean pushRequired = true;

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

    public Optional<String> getTitleDe() {
        return titleDe;
    }

    public void setTitleDe(Optional<String> titleDe) {
        this.titleDe = titleDe;
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

    public Optional<String> getContentDe() {
        return contentDe;
    }

    public void setContentDe(Optional<String> contentDe) {
        this.contentDe = contentDe;
    }

    public Optional<Long> getDate() {
        return date;
    }

    public void setDate(Optional<Long> date) {
        this.date = date;
    }

    public Optional<String> getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(Optional<String> sourceLink) {
        this.sourceLink = sourceLink;
    }

    public Optional<List<String>> getTags() {
        return tags;
    }

    public void setTags(Optional<List<String>> tags) {
        this.tags = tags;
    }

    public Optional<List<String>> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(Optional<List<String>> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public Optional<List<VideoDTO>> getVideos() {
        return videos;
    }

    public void setVideos(Optional<List<VideoDTO>> videos) {
        this.videos = videos;
    }

    public Set<Long> getPublishTo() {
        return publishTo;
    }

    public void setPublishTo(Set<Long> publishTo) {
        this.publishTo = publishTo;
    }

    public boolean isPushRequired() {
        return pushRequired;
    }

    public void setPushRequired(boolean pushRequired) {
        this.pushRequired = pushRequired;
    }
}
