package ru.smartel.strike.dto.request.comment;

import ru.smartel.strike.entity.interfaces.HasComments;

public interface CommentDTOWithOwner {
    CommentOwnerDTO<? extends HasComments> getOwner();
    void setOwner(CommentOwnerDTO<? extends HasComments> owner);
}
