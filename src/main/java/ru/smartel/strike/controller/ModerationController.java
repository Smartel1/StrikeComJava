package ru.smartel.strike.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.comment.CommentListRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.comment.CommentDTO;
import ru.smartel.strike.dto.response.moderation.DashboardDTO;
import ru.smartel.strike.service.comment.CommentService;
import ru.smartel.strike.service.event.EventService;
import ru.smartel.strike.service.news.NewsService;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/{locale}/moderation")
@Validated
@PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
public class ModerationController {

    private CommentService commentService;
    private EventService eventService;
    private NewsService newsService;

    public ModerationController(CommentService commentService,
                                EventService eventService,
                                NewsService newsService) {
        this.commentService = commentService;
        this.eventService = eventService;
        this.newsService = newsService;
    }

    @GetMapping("dashboard")
    public DashboardDTO dashboard (
    ) {
        return new DashboardDTO(
                commentService.getComplainedCount(),
                eventService.getNonPublishedCount(),
                newsService.getNonPublishedCount()
        );
    }

    @GetMapping("claim-comment")
    public ListWrapperDTO<CommentDTO> complainedCommentsList (
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestBody CommentListRequestDTO dto
    ) {
        dto.mergeWith(page, perPage);

        return commentService.getComplained(dto);
    }
}
