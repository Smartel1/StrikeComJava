package ru.smartel.strike.service.news;

import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface NewsDTOValidator {
    void validateListQueryDTO(NewsListRequestDTO dto) throws DTOValidationException;
    void validateStoreDTO(NewsRequestDTO dto) throws DTOValidationException;
    void validateUpdateDTO(NewsRequestDTO dto) throws DTOValidationException;
}
