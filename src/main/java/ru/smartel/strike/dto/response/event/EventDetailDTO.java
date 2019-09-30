package ru.smartel.strike.dto.response.event;

import lombok.Data;
import ru.smartel.strike.dto.response.TitlesContentExtendableDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.reference.locality.ExtendedLocalityDTO;
import ru.smartel.strike.dto.response.user.UserDTO;
import ru.smartel.strike.dto.response.video.VideoDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.Photo;
import ru.smartel.strike.entity.Tag;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class EventDetailDTO extends TitlesContentExtendableDTO {

    public EventDetailDTO(Event event, Locale locale) {
        super(event, locale);
        id = event.getId();
        published = event.isPublished();
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
        author = null != event.getAuthor() ? new UserDTO(event.getAuthor()) : null;
        conflict = new ConflictDetailDTO(event.getConflict(), locale);
        commentsCount = event.getComments().size();
        locality = null != event.getLocality() ? new ExtendedLocalityDTO(event.getLocality(), locale) : null;
    }

    private int id;
    private boolean published;
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
    private UserDTO author;
    private ConflictDetailDTO conflict;
    private int commentsCount;
    private ExtendedLocalityDTO locality;
}
