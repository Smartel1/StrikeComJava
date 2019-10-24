package ru.smartel.strike.service;

import ru.smartel.strike.dto.request.post.PostListRequestDTO;
import ru.smartel.strike.dto.request.post.PostRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface PostDTOValidator {
    void validateListQueryDTO(PostListRequestDTO dto) throws DTOValidationException;
    void validateStoreDTO(PostRequestDTO dto) throws DTOValidationException;
    void validateUpdateDTO(PostRequestDTO dto) throws DTOValidationException;
}
