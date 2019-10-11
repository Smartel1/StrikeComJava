package ru.smartel.strike.dto.response.event;

import ru.smartel.strike.dto.response.TitlesContentExtendableDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.video.VideoDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.Photo;
import ru.smartel.strike.entity.Tag;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class EventListDTO extends TitlesContentExtendableDTO {

    public EventListDTO(Event event, Locale locale) {
        super(event, locale);
        id = event.getId();
        date = event.getDate().toEpochSecond(ZoneOffset.UTC);
        views = event.getViews();
        latitude = event.getLatitude();
        longitude = event.getLongitude();
        sourceLink = event.getSourceLink();
        conflictId = event.getConflict().getId();
        eventStatusId = null != event.getStatus() ? event.getStatus().getId() : null;
        eventTypeId = null != event.getType() ? event.getType().getId() : null;
        photos = event.getPhotos().stream().map(Photo::getUrl).collect(Collectors.toList());
        videos = event.getVideos().stream().map(VideoDTO::new).collect(Collectors.toList());
        tags = event.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        conflict = new ConflictDetailDTO(event.getConflict(), locale);
        commentsCount = event.getComments().size();
    }

    private int id;
    private long date;
    private int views;
    private double latitude;
    private double longitude;
    private String sourceLink;
    private int conflictId;
    private Integer eventStatusId;
    private Integer eventTypeId;
    private List<String> photos;
    private List<VideoDTO> videos;
    private List<String> tags;
    private ConflictDetailDTO conflict;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public int getConflictId() {
        return conflictId;
    }

    public void setConflictId(int conflictId) {
        this.conflictId = conflictId;
    }

    public Integer getEventStatusId() {
        return eventStatusId;
    }

    public void setEventStatusId(Integer eventStatusId) {
        this.eventStatusId = eventStatusId;
    }

    public Integer getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId) {
        this.eventTypeId = eventTypeId;
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

    public ConflictDetailDTO getConflict() {
        return conflict;
    }

    public void setConflict(ConflictDetailDTO conflict) {
        this.conflict = conflict;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
