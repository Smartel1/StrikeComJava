package ru.smartel.strike.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
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
    private List<Photo> photos = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(name = "event_video",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "video_id", referencedColumnName = "id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Video> videos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "event_tag",
            joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Tag> tags  = Collections.emptyList();

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
    private List<Comment> comments = Collections.emptyList();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locality_id")
    private Locality locality;
}
