package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.comment.*;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.comment.CommentDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.comment.CommentService;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/{locale}/{entity}/{entity-id}/comment")
@Validated
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ListWrapperDTO<CommentDTO> list (
            @PathVariable("entity") String entity,
            @PathVariable("entity-id") int entityId,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestBody CommentListRequestDTO dto
    ) {
        fillDTOWithCommentOwner(dto, entityId, entity);

        dto.mergeWith(page, perPage);

        return commentService.list(dto);
    }

    @PostMapping
    public CommentDTO create (
            @PathVariable("entity") String entity,
            @PathVariable("entity-id") int entityId,
            @RequestBody CommentCreateRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        fillDTOWithCommentOwner(dto, entityId, entity);
        dto.setUser(user);
        return commentService.create(dto);
    }

    @PutMapping("{comment-id}")
    public CommentDTO update (
            @PathVariable("entity") String entity,
            @PathVariable("entity-id") int entityId,
            @PathVariable("comment-id") int commentId,
            @RequestBody CommentUpdateRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        fillDTOWithCommentOwner(dto, entityId, entity);
        dto.setCommentId(commentId);
        dto.setUser(user);
        return commentService.update(dto);
    }

    @DeleteMapping("{comment-id}")
    public void delete (
            @PathVariable("entity") String entity,
            @PathVariable("entity-id") int entityId,
            @PathVariable("comment-id") int commentId,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        commentService.delete(commentId, user.getId());
    }

    /**
     * Fill DTO with owner (event/news)
     * @param dto dto to fill
     * @param ownerId news/event id
     * @param entity name of owner entity from request path
     */
    private void fillDTOWithCommentOwner(CommentDTOWithOwner dto, int ownerId, String entity) {
        switch (entity) {
            case "event": dto.setOwner(new CommentOwnerDTO<>(ownerId, Event.class)); break;
            case "news": dto.setOwner(new CommentOwnerDTO<>(ownerId, News.class)); break;
            default: throw new IllegalArgumentException("cannot read comments for entity " + entity);
        }
    }
}
