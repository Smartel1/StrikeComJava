package ru.smartel.strike.service.comment;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.dto.request.comment.CommentRequestDTO;
import ru.smartel.strike.dto.request.comment.CommentListRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.comment.CommentDTO;
import ru.smartel.strike.exception.DTOValidationException;

/**
 * This service is generic because Comments may belong to different entities (Events and News atm)
 */
public interface CommentService {
    ListWrapperDTO<CommentDTO> list(CommentListRequestDTO dto);
    ListWrapperDTO<CommentDTO> getComplained(BaseListRequestDTO dto);
    Long getComplainedCount();
    CommentDTO create(CommentRequestDTO dto, Integer userId) throws DTOValidationException;
    CommentDTO update(Integer commentId, CommentRequestDTO dto, Integer userId) throws DTOValidationException;
    void delete(Integer commentId, Integer userId) throws DTOValidationException;
}
