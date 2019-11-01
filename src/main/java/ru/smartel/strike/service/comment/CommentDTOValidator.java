package ru.smartel.strike.service.comment;

import ru.smartel.strike.dto.request.comment.CommentRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface CommentDTOValidator {
    void validateDTO(CommentRequestDTO dto) throws DTOValidationException;
}
