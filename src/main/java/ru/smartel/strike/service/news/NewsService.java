package ru.smartel.strike.service.news;

import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.news.NewsDetailDTO;
import ru.smartel.strike.dto.response.news.NewsListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;

public interface NewsService {

    Long getNonPublishedCount();

    ListWrapperDTO<NewsListDTO> list(NewsListRequestDTO dto, Locale locale, User user)
            throws DTOValidationException;

    NewsDetailDTO incrementViewsAndGet(Integer newsId, Locale locale);

    void setFavourite(Integer newsId, int userId, boolean isFavourite);

    NewsDetailDTO create(NewsRequestDTO dto, Integer userId, Locale locale)
            throws BusinessRuleValidationException, DTOValidationException;

    NewsDetailDTO update(Integer newsId, NewsRequestDTO dto, Integer userId, Locale locale)
            throws BusinessRuleValidationException, DTOValidationException;

    void delete(Integer newsId) throws BusinessRuleValidationException;
}
