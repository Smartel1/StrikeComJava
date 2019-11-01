package ru.smartel.strike.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.entity.interfaces.HasComments;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentRequestDTO implements CommentDTOWithOwner {

    @JsonIgnore
    private CommentOwnerDTO<? extends HasComments> owner;
    private String content;
    private List<String> photoUrls;

    public CommentOwnerDTO<? extends HasComments> getOwner() {
        return owner;
    }

    public void setOwner(CommentOwnerDTO<? extends HasComments> owner) {
        this.owner = owner;
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
