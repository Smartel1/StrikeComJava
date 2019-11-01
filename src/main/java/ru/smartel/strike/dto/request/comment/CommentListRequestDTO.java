package ru.smartel.strike.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.interfaces.HasComments;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentListRequestDTO extends BaseListRequestDTO implements CommentDTOWithOwner {

    @JsonIgnore
    private CommentOwnerDTO<? extends HasComments> owner;

    public CommentOwnerDTO<? extends HasComments> getOwner() {
        return owner;
    }

    public void setOwner(CommentOwnerDTO<? extends HasComments> owner) {
        this.owner = owner;
    }
}
