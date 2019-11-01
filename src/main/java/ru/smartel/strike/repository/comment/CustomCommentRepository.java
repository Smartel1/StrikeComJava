package ru.smartel.strike.repository.comment;

import org.springframework.stereotype.Repository;
import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.dto.request.comment.CommentListRequestDTO;
import ru.smartel.strike.entity.Comment;

import java.util.List;

@Repository
public interface CustomCommentRepository {
    List<Comment> getCommentsOfEntityWithPaginationOrderByCreationDate(CommentListRequestDTO dto);
    Long getCommentsOfEntityCount(CommentListRequestDTO dto);
    List<Comment> getCommentsWithClaims(BaseListRequestDTO dto);
    Long getCommentsWithClaimsCount();
}
