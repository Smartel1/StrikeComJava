package ru.smartel.strike.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CommentUpdateRequestDTO extends CommentCreateRequestDTO {

    @JsonIgnore
    private Integer commentId;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
}
