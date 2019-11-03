package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsShowDetailRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.news.NewsDetailDTO;
import ru.smartel.strike.dto.response.news.NewsListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.news.NewsService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/{locale}")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public ListWrapperDTO<NewsListDTO> index(
            NewsListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        dto.setUser(user);
        return newsService.list(dto);
    }

    @PostMapping(value = "/news-list")
    public ListWrapperDTO postIndex(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestBody NewsListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        //alias of index method
        dto.setLocale(locale);
        dto.mergeWith(page, perPage);
        return index(dto, user);
    }

    @GetMapping("/news/{id}")
    public NewsDetailDTO show(
            NewsShowDetailRequestDTO dto
    ) {
        return newsService.incrementViewsAndGet(dto);
    }

    @PostMapping("/news/{id}/favourite")
    public void setFavourite(
            @PathVariable("id") int newsId,
            @RequestParam(value = "favourite") boolean isFavourite,
            @AuthenticationPrincipal User user
    ) {
        Optional.ofNullable(user).ifPresent(
                usr -> newsService.setFavourite(newsId, usr.getId(), isFavourite)
        );
    }

    @PostMapping(path = "/news")
    public NewsDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody NewsCreateRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws BusinessRuleValidationException, DTOValidationException {
        dto.setLocale(locale);
        dto.setUser(user);
        return newsService.create(dto);
    }

    @PutMapping(path = "/news/{id}")
    public NewsDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int newsId,
            @AuthenticationPrincipal User user,
            @RequestBody NewsUpdateRequestDTO dto
    ) throws BusinessRuleValidationException, DTOValidationException {
        dto.setNewsId(newsId);
        dto.setLocale(locale);
        dto.setUser(user);
        return newsService.update(dto);
    }

    @DeleteMapping(path = "/news/{id}")
    public void delete(
            @PathVariable("id") int newsId
    ) throws BusinessRuleValidationException {
        newsService.delete(newsId);
    }
}
