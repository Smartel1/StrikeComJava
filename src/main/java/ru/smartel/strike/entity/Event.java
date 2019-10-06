package ru.smartel.strike.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.AccessType;
import ru.smartel.strike.entity.interfaces.*;
import ru.smartel.strike.entity.reference.EventStatus;
import ru.smartel.strike.entity.reference.EventType;
import ru.smartel.strike.entity.reference.Locality;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "Event")
@Table(name = "events")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event implements Commentable, Post, TitlesContents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @AccessType(AccessType.Type.PROPERTY) //чтобы доставать id из прокси (без загрузки объекта из базы)
    private int id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "title_es")
    private String titleEs;

    @Column(name = "content_ru", columnDefinition = "TEXT")
    private String contentRu;

    @Column(name = "content_en", columnDefinition = "TEXT")
    private String contentEn;

    @Column(name = "content_es", columnDefinition = "TEXT")
    private String contentEs;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "views", nullable = false)
    private Integer views = 0;

    @Column(name = "published", nullable = false)
    private boolean published = false;

    @Size(max = 500)
    @Column(name = "source_link", length = 500)
    private String sourceLink;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(name = "event_photo",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "photo_id", referencedColumnName = "id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Photo> photos = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(name = "event_video",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "video_id", referencedColumnName = "id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Video> videos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "event_tag",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conflict_id")
    private Conflict conflict;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_status_id")
    @AccessType(AccessType.Type.PROPERTY)
    private EventStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_type_id")
    private EventType type;

    @ManyToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(name = "comment_event",
            inverseJoinColumns = {@JoinColumn(name = "comment_id")},
            joinColumns = {@JoinColumn(name = "event_id")}
    )
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String getTitleRu() {
        return titleRu;
    }

    @Override
    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    @Override
    public String getTitleEn() {
        return titleEn;
    }

    @Override
    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    @Override
    public String getTitleEs() {
        return titleEs;
    }

    @Override
    public void setTitleEs(String titleEs) {
        this.titleEs = titleEs;
    }

    @Override
    public String getContentRu() {
        return contentRu;
    }

    @Override
    public void setContentRu(String contentRu) {
        this.contentRu = contentRu;
    }

    @Override
    public String getContentEn() {
        return contentEn;
    }

    @Override
    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
    }

    @Override
    public String getContentEs() {
        return contentEs;
    }

    @Override
    public void setContentEs(String contentEs) {
        this.contentEs = contentEs;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Conflict getConflict() {
        return conflict;
    }

    public void setConflict(Conflict conflict) {
        this.conflict = conflict;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public Set<Comment> getComments() {
        return comments;
    }

    @Override
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }
}
