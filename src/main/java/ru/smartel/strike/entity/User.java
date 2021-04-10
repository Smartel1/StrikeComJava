package ru.smartel.strike.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.TextNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.AccessType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@TypeDef(
        name = "jsonb",
        typeClass = JsonNodeBinaryType.class
)
public class User {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MODERATOR = "MODERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @AccessType(AccessType.Type.PROPERTY) //чтобы доставать id из прокси (без загрузки объекта из базы)
    private long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(unique = true)
    private String uid;

    @Column
    private String name;

    @Column
    private String fcm; //FCM registration token (firebase cloud messaging)

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column
    private String email;

    @Column
    @Type(type = "jsonb")
    private JsonNode roles = new ArrayNode(JsonNodeFactory.instance);

    @ManyToMany
    @JoinTable(
            name = "favourite_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> favouriteEvents;

    @ManyToMany
    @JoinTable(
            name = "favourite_news",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "news_id")
    )
    private List<News> favouriteNews;

    @ManyToMany
    @JoinTable(
            name = "favourite_conflicts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "conflict_id")
    )
    private List<Conflict> favouriteConflicts;

    public List<String> getRolesAsList() {
        List<String> roles = new ArrayList<>();
        for (JsonNode role : this.roles) {
            roles.add(role.asText());
        }
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = new ArrayNode(
                JsonNodeFactory.instance,
                roles.stream()
                        .map(TextNode::valueOf)
                        .collect(Collectors.toList())
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uuid) {
        this.uid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JsonNode getRoles() {
        return roles;
    }

    public void setRoles(JsonNode roles) {
        this.roles = roles;
    }

    public List<Event> getFavouriteEvents() {
        return favouriteEvents;
    }

    public void setFavouriteEvents(List<Event> favouriteEvents) {
        this.favouriteEvents = favouriteEvents;
    }

    public List<News> getFavouriteNews() {
        return favouriteNews;
    }

    public void setFavouriteNews(List<News> favouriteNews) {
        this.favouriteNews = favouriteNews;
    }

    public List<Conflict> getFavouriteConflicts() {
        return favouriteConflicts;
    }

    public void setFavouriteConflicts(List<Conflict> favouriteConflicts) {
        this.favouriteConflicts = favouriteConflicts;
    }
}
