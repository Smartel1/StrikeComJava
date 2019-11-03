package ru.smartel.strike.service.news;

import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface NewsDTOValidator {
    void validateListQueryDTO(NewsListRequestDTO dto) throws DTOValidationException;
    void validateStoreDTO(NewsCreateRequestDTO dto) throws DTOValidationException;
    void validateUpdateDTO(NewsUpdateRequestDTO dto) throws DTOValidationException;
}
