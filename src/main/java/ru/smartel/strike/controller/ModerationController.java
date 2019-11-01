package ru.smartel.strike.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.comment.CommentListRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.comment.CommentDTO;
import ru.smartel.strike.service.comment.CommentService;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/{locale}/moderation")
@Validated
public class ModerationController {

    private CommentService commentService;

    public ModerationController(CommentService commentService) {
        this.commentService = commentService;
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
