package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.conflict.ConflictListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.ConflictService;
import ru.smartel.strike.service.Locale;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/{locale}")
public class ConflictController {

    private ConflictService conflictService;

    public ConflictController(ConflictService conflictService) {
        this.conflictService = conflictService;
    }

    @GetMapping("/conflict")
    public ListWrapperDTO<ConflictListDTO> index(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(name = "brief", required = false, defaultValue = "false") Boolean brief,
            @RequestBody ConflictListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        return conflictService.list(dto, perPage, page, locale, user, brief);
    }

    @PostMapping("/conflict-list")
    public ListWrapperDTO<ConflictListDTO> postIndex(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(name = "brief", required = false, defaultValue = "false") Boolean brief,
            @RequestBody ConflictListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        //alias of index method
        return index(locale, perPage, page, brief, dto, user);
    }

    @GetMapping("/conflict/{id}")
    public ConflictDetailDTO show(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int conflictId
    ) {
        return conflictService.get(conflictId, locale);
    }

    @PostMapping(path = "/conflict", consumes = {"application/json"})
    public ConflictDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody ConflictRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws BusinessRuleValidationException, DTOValidationException {
        return conflictService.create(dto, null != user? user.getId() : null, locale);
    }

    @PutMapping(path = "/conflict/{id}", consumes = {"application/json"})
    public ConflictDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int conflictId,
            @AuthenticationPrincipal User user,
            @RequestBody ConflictRequestDTO dto
    ) throws BusinessRuleValidationException, DTOValidationException {
        return conflictService.update(conflictId, dto, null != user? user.getId() : null, locale);
    }

    @DeleteMapping(path = "/conflict/{id}")
    public void delete(
            @PathVariable("id") int conflictId
    ) throws BusinessRuleValidationException {
        conflictService.delete(conflictId);
    }
}
