package ru.smartel.strike.service.news;

import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsShowDetailRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.news.NewsDetailDTO;
import ru.smartel.strike.dto.response.news.NewsListDTO;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;

public interface NewsService {

    Long getNonPublishedCount();

    ListWrapperDTO<NewsListDTO> list(NewsListRequestDTO dto)
            throws DTOValidationException;

    NewsDetailDTO incrementViewsAndGet(NewsShowDetailRequestDTO dto);

    void setFavourite(Integer newsId, int userId, boolean isFavourite);

    NewsDetailDTO create(NewsCreateRequestDTO dto)
            throws BusinessRuleValidationException, DTOValidationException;

    NewsDetailDTO update(NewsUpdateRequestDTO dto)
            throws BusinessRuleValidationException, DTOValidationException;

    void delete(Integer newsId) throws BusinessRuleValidationException;
}
