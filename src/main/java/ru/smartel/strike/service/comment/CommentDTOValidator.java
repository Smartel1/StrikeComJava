package ru.smartel.strike.service.comment;

import ru.smartel.strike.dto.request.comment.CommentCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface CommentDTOValidator {
    void validateDTO(CommentCreateRequestDTO dto) throws DTOValidationException;
}
