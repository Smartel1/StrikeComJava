package ru.smartel.strike.dto.request;


import com.fasterxml.jackson.annotation.*;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.validation.Exists;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventStoreDTO {

    @Exists(entity = Conflict.class)
    @JsonProperty("conflict_id")
    private Integer conflictId;
    private boolean conflictIdReceived;

    @NotNull
    private LocalDateTime date;

    private Boolean published;
    private boolean publishedReceived;

    private String title;
    private boolean titleReceived;

    @JsonProperty("title_ru")
    private String titleRu;
    private boolean titleRuReceived;

    @JsonProperty("title_en")
    private String titleEn;
    private boolean titleEnReceived;

    @JsonProperty("title_es")
    private String titleEs;
    private boolean titleEsReceived;

    @NotNull
    private Float latitude;
    @NotNull
    private Float longitude;

    @JsonProperty("source_link")
    private String sourceLink;
    private boolean sourceLinkReceived;

    @JsonProperty("locality_id")
    private Integer localityId;
    private boolean localityIdReceived;

    @JsonProperty("event_status_id")
    private Integer eventStatusId;
    private boolean eventStatusIdReceived;

    @JsonProperty("event_type_id")
    private Integer eventTypeId;
    private boolean eventTypeIdReceived;

    public Integer getConflictId() {
        return conflictId;
    }

    public void setConflictId(Integer conflictId) {
        this.conflictId = conflictId;
        conflictIdReceived = true;
    }

    public boolean isConflictIdReceived() {
        return conflictIdReceived;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @JsonProperty
    public void setDate(Long date) {
        this.date = LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC);
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
        publishedReceived = true;
    }

    public boolean isPublishedReceived() {
        return publishedReceived;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        titleReceived = true;
    }

    public boolean isTitleReceived() {
        return titleReceived;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
        titleRuReceived = true;
    }

    public boolean isTitleRuReceived() {
        return titleRuReceived;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
        titleEnReceived = true;
    }

    public boolean isTitleEnReceived() {
        return titleEnReceived;
    }

    public String getTitleEs() {
        return titleEs;
    }

    public void setTitleEs(String titleEs) {
        this.titleEs = titleEs;
        titleEsReceived = true;
    }

    public boolean isTitleEsReceived() {
        return titleEsReceived;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
        sourceLinkReceived = true;
    }

    public boolean isSourceLinkReceived() {
        return sourceLinkReceived;
    }

    public Integer getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
        localityIdReceived = true;
    }

    public boolean isLocalityIdReceived() {
        return localityIdReceived;
    }

    public Integer getEventStatusId() {
        return eventStatusId;
    }

    public void setEventStatusId(Integer eventStatusId) {
        this.eventStatusId = eventStatusId;
        eventStatusIdReceived = true;
    }

    public boolean isEventStatusIdReceived() {
        return eventStatusIdReceived;
    }

    public Integer getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId) {
        this.eventTypeId = eventTypeId;
        eventTypeIdReceived = true;
    }

    public boolean isEventTypeIdReceived() {
        return eventTypeIdReceived;
    }

//    private List<String> tags;
//    private List<String> photoURLs;
//    private List<VideoDTO> videos;
}
