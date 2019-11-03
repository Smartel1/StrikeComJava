package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.conflict.ConflictListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.conflict.ConflictService;
import ru.smartel.strike.service.Locale;

@RestController
@RequestMapping("/api/v1/{locale}")
public class ConflictController {

    private ConflictService conflictService;

    public ConflictController(ConflictService conflictService) {
        this.conflictService = conflictService;
    }

    @GetMapping("/conflict")
    public ListWrapperDTO<ConflictListDTO> index(
            ConflictListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        dto.setUser(user);
        return conflictService.list(dto);
    }

    @PostMapping("/conflict-list")
    public ListWrapperDTO<ConflictListDTO> postIndex(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "brief", required = false) Boolean brief,
            @RequestBody ConflictListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        //alias of index method
        dto.mergeWith(page, perPage);
        dto.setLocale(locale);
        if (null != brief) dto.setBrief(brief);
        return index(dto, user);
    }

    @GetMapping("/conflict/{id}")
    public ConflictDetailDTO show(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int conflictId
    ) {
        return conflictService.get(conflictId, locale);
    }

    @PostMapping(path = "/conflict")
    public ConflictDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody ConflictCreateRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws BusinessRuleValidationException, DTOValidationException {
        dto.setLocale(locale);
        dto.setUser(user);
        return conflictService.create(dto);
    }

    @PutMapping(path = "/conflict/{id}")
    public ConflictDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int conflictId,
            @AuthenticationPrincipal User user,
            @RequestBody ConflictUpdateRequestDTO dto
    ) throws BusinessRuleValidationException, DTOValidationException {
        dto.setLocale(locale);
        dto.setUser(user);
        dto.setConflictId(conflictId);
        return conflictService.update(dto);
    }

    @DeleteMapping(path = "/conflict/{id}")
    public void delete(
            @PathVariable("id") int conflictId
    ) throws BusinessRuleValidationException {
        conflictService.delete(conflictId);
    }
}
