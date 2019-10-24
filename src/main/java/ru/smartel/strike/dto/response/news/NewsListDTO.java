package ru.smartel.strike.dto.response.news;

import ru.smartel.strike.dto.response.TitlesContentExtendableDTO;
import ru.smartel.strike.dto.response.video.VideoDTO;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.entity.Photo;
import ru.smartel.strike.entity.Tag;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class NewsListDTO extends TitlesContentExtendableDTO {

    public NewsListDTO(News news, Locale locale) {
        super(news, locale);
        id = news.getId();
        date = news.getDate().toEpochSecond(ZoneOffset.UTC);
        views = news.getViews();
        sourceLink = news.getSourceLink();
        photos = news.getPhotos().stream().map(Photo::getUrl).collect(Collectors.toList());
        videos = news.getVideos().stream().map(VideoDTO::new).collect(Collectors.toList());
        tags = news.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        commentsCount = news.getComments().size();
    }

    private int id;
    private long date;
    private int views;
    private String sourceLink;
    private List<String> photos;
    private List<VideoDTO> videos;
    private List<String> tags;
    private int commentsCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
