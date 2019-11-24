package ru.smartel.strike.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.response.DetailWrapperDTO;
import ru.smartel.strike.dto.response.moderation.DashboardDTO;
import ru.smartel.strike.service.event.EventService;
import ru.smartel.strike.service.news.NewsService;

@RestController
@RequestMapping("/api/v2/{locale}/moderation")
@Validated
@PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
public class ModerationController {

    private EventService eventService;
    private NewsService newsService;

    public ModerationController(EventService eventService, NewsService newsService) {
        this.eventService = eventService;
        this.newsService = newsService;
    }

    @GetMapping("dashboard")
    public DetailWrapperDTO<DashboardDTO> dashboard() {
        return new DetailWrapperDTO<>(
                DashboardDTO.of(eventService.getNonPublishedCount(), newsService.getNonPublishedCount()));
    }
}
