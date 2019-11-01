package ru.smartel.strike.dto.response.comment;

import ru.smartel.strike.dto.response.claim.ClaimCountForTypeDTO;
import ru.smartel.strike.dto.response.user.UserDTO;
import ru.smartel.strike.entity.Comment;
import ru.smartel.strike.entity.Photo;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class CommentDTO {
    private int id;
    private UserDTO user;
    private String content;
    private Long createdAt;
    private List<String> photos;
    private List<ClaimCountForTypeDTO> claims;

    public CommentDTO(Comment comment) {
        id = comment.getId();

        user = Optional.ofNullable(comment.getUser())
                .map(UserDTO::new)
                .orElse(null);

        content = comment.getContent();

        createdAt = Optional.ofNullable(comment.getCreatedAt())
                .map((t) -> t.toEpochSecond(ZoneOffset.UTC))
                .orElse(null);

        photos = comment.getPhotos().stream()
                .map(Photo::getUrl)
                .collect(toList());

        this.claims = comment.getClaims().stream()
                .collect(
                        groupingBy(
                                claim -> claim.getClaimType().getId(),
                                counting()
                        )
                )
                .entrySet().stream()
                .map(entry -> new ClaimCountForTypeDTO(entry.getKey(), entry.getValue()))
                .collect(toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<ClaimCountForTypeDTO> getClaims() {
        return claims;
    }

    public void setClaims(List<ClaimCountForTypeDTO> claims) {
        this.claims = claims;
    }
}
