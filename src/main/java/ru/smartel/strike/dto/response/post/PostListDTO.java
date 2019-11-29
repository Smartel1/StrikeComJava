package ru.smartel.strike.dto.response.post;

import ru.smartel.strike.dto.response.TitlesContentExtendableDTO;
import ru.smartel.strike.dto.response.video.VideoDTO;
import ru.smartel.strike.entity.Photo;
import ru.smartel.strike.entity.Tag;
import ru.smartel.strike.entity.interfaces.PostEntity;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PostListDTO extends TitlesContentExtendableDTO {

    protected long id;
    protected boolean published;
    protected long date;
    protected int views;
    protected String sourceLink;
    protected List<String> photos;
    protected List<VideoDTO> videos;
    protected List<String> tags;

    protected void setCommonFieldsOf(PostEntity post, Locale locale) {
        setContentsOf(post, locale);
        published = post.isPublished();
        id = post.getId();
        date = post.getDate().toEpochSecond(ZoneOffset.UTC);
        views = post.getViews();
        sourceLink = post.getSourceLink();
        photos = post.getPhotos().stream().map(Photo::getUrl).collect(Collectors.toList());
        videos = post.getVideos().stream().map(VideoDTO::from).collect(Collectors.toList());
        tags = post.getTags().stream().map(Tag::getName).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<VideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
