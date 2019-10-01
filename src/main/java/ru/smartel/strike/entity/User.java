package ru.smartel.strike.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import lombok.Data;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@TypeDef(
        name = "jsonb",
        typeClass = JsonNodeBinaryType.class
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MODERATOR = "MODERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @AccessType(AccessType.Type.PROPERTY) //чтобы доставать id из прокси (без загрузки объекта из базы)
    private int id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(unique = true)
    private String uuid;

    @Column
    private String name;

    @Column
    private String fcm;

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

    public List<String> getRolesAsList() {
        List<String> roles = new ArrayList<>();
        for (JsonNode role : this.roles) {
            roles.add(role.asText());
        }
        return roles;
    }
}
