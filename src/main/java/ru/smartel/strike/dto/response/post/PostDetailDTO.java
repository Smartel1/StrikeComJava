package ru.smartel.strike.dto.response.post;

import ru.smartel.strike.dto.response.TitlesContentExtendableDTO;
import ru.smartel.strike.dto.response.user.UserDTO;
import ru.smartel.strike.dto.response.video.VideoDTO;
import ru.smartel.strike.entity.Photo;
import ru.smartel.strike.entity.Tag;
import ru.smartel.strike.entity.interfaces.PostEntity;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostDetailDTO extends TitlesContentExtendableDTO {

    private int id;
    private boolean published;
    private long date;
    private int views;
    private String sourceLink;
    private List<String> photos;
    private List<VideoDTO> videos;
    private List<String> tags;
    private UserDTO author;
    private int commentsCount;

    public PostDetailDTO(PostEntity post, Locale locale) {
        super(post, locale);
        id = post.getId();
        published = post.isPublished();
        date = post.getDate().toEpochSecond(ZoneOffset.UTC);
        views = post.getViews();
        sourceLink = post.getSourceLink();
        photos = post.getPhotos().stream().map(Photo::getUrl).collect(Collectors.toList());
        videos = post.getVideos().stream().map(VideoDTO::new).collect(Collectors.toList());
        tags = post.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        author = Optional.ofNullable(post.getAuthor()).map(UserDTO::new).orElse(null);
        commentsCount = post.getComments().size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
