package ru.smartel.strike.service.news;

import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;

public interface NewsDTOValidator {
    void validateListQueryDTO(NewsListRequestDTO dto);
    void validateStoreDTO(NewsCreateRequestDTO dto);
    void validateUpdateDTO(NewsUpdateRequestDTO dto);
}
