package ru.smartel.strike.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.entity.interfaces.HasComments;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentCreateRequestDTO implements CommentDTOWithOwner {

    @JsonIgnore
    private CommentOwnerDTO<? extends HasComments> owner;
    @JsonIgnore
    private User user;
    private String content;
    private List<String> photoUrls;

    public CommentOwnerDTO<? extends HasComments> getOwner() {
        return owner;
    }

    public void setOwner(CommentOwnerDTO<? extends HasComments> owner) {
        this.owner = owner;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}
