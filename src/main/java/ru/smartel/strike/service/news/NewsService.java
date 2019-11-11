package ru.smartel.strike.service.news;

import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsShowDetailRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.news.NewsDetailDTO;
import ru.smartel.strike.dto.response.news.NewsListDTO;

public interface NewsService {

    Long getNonPublishedCount();

    ListWrapperDTO<NewsListDTO> list(NewsListRequestDTO dto);

    NewsDetailDTO incrementViewsAndGet(NewsShowDetailRequestDTO dto);

    void setFavourite(Long newsId, long userId, boolean isFavourite);

    NewsDetailDTO create(NewsCreateRequestDTO dto);

    NewsDetailDTO update(NewsUpdateRequestDTO dto);

    void delete(Long newsId);
}
