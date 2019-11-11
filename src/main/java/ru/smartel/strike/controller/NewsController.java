package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsShowDetailRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.news.NewsDetailDTO;
import ru.smartel.strike.dto.response.news.NewsListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.news.NewsService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v2/{locale}/news")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ListWrapperDTO<NewsListDTO> index(
            NewsListRequestDTO dto,
            @AuthenticationPrincipal User user) {
        dto.setUser(user);
        return newsService.list(dto);
    }

    @GetMapping("{id}")
    public NewsDetailDTO show(NewsShowDetailRequestDTO dto) {
        return newsService.incrementViewsAndGet(dto);
    }

    @PostMapping("{id}/favourites")
    public void setFavourite(
            @PathVariable("id") long newsId,
            @RequestParam(value = "favourite") boolean isFavourite,
            @AuthenticationPrincipal User user) {
        Optional.ofNullable(user).ifPresent(
                usr -> newsService.setFavourite(newsId, usr.getId(), isFavourite));
    }

    @PostMapping
    public NewsDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody NewsCreateRequestDTO dto,
            @AuthenticationPrincipal User user) {
        dto.setLocale(locale);
        dto.setUser(user);
        return newsService.create(dto);
    }

    @PutMapping("{id}")
    public NewsDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") long newsId,
            @AuthenticationPrincipal User user,
            @RequestBody NewsUpdateRequestDTO dto ) {
        dto.setNewsId(newsId);
        dto.setLocale(locale);
        dto.setUser(user);
        return newsService.update(dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long newsId) {
        newsService.delete(newsId);
    }
}
