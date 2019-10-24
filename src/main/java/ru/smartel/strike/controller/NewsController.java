package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.news.NewsDetailDTO;
import ru.smartel.strike.dto.response.news.NewsListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.NewsService;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/{locale}")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public ListWrapperDTO<NewsListDTO> index(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestBody NewsListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        return newsService.list(dto, perPage, page, locale, user);
    }

    @PostMapping("/news-list")
    public ListWrapperDTO postIndex(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestBody NewsListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        //alias of index method
        return index(locale, perPage, page, dto, user);
    }

    @GetMapping("/news/{id}")
    public NewsDetailDTO show(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int newsId
    ) {
        return newsService.incrementViewsAndGet(newsId, locale);
    }

    @PostMapping("/news/{id}/favourite")
    public void setFavourite(
            @PathVariable("id") int newsId,
            @RequestParam(value = "favourite") boolean isFavourite,
            @AuthenticationPrincipal User user
    ) {
        newsService.setFavourite(newsId, null != user? user.getId() : null, isFavourite);
    }

    @PostMapping(path = "/news", consumes = {"application/json"})
    public NewsDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody NewsRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws BusinessRuleValidationException, DTOValidationException {
        return newsService.create(dto, null != user? user.getId() : null, locale);
    }

    @PutMapping(path = "/news/{id}", consumes = {"application/json"})
    public NewsDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int newsId,
            @AuthenticationPrincipal User user,
            @RequestBody NewsRequestDTO dto
    ) throws BusinessRuleValidationException, DTOValidationException {
        return newsService.update(newsId, dto, null != user? user.getId() : null, locale);
    }

    @DeleteMapping(path = "/news/{id}")
    public void delete(
            @PathVariable("id") int newsId
    ) throws BusinessRuleValidationException {
        newsService.delete(newsId);
    }
}
