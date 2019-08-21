package ru.smartel.strike.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MODERATOR = "MODERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
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
    private String roles = "[]";

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String[] getRoles() throws IOException {
        return new ObjectMapper().readValue(roles, String[].class);
    }

    public void setRoles(String[] roles) throws JsonProcessingException {
        this.roles = new ObjectMapper().writeValueAsString(roles);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
