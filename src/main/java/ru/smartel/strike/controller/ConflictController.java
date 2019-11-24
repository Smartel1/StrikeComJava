package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.conflict.ConflictListDTO;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.conflict.ConflictService;

@RestController
@RequestMapping("/api/v2/{locale}/conflicts")
public class ConflictController {

    private ConflictService conflictService;

    public ConflictController(ConflictService conflictService) {
        this.conflictService = conflictService;
    }

    @GetMapping
    public ListWrapperDTO<ConflictListDTO> index(
            ConflictListRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal user) {
        dto.setUser(user);
        return conflictService.list(dto);
    }

    @GetMapping("{id}")
    public ConflictDetailDTO show(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") long conflictId) {
        return conflictService.get(conflictId, locale);
    }

    @PostMapping
    public ConflictDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody ConflictCreateRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal user) {
        dto.setLocale(locale);
        dto.setUser(user);
        return conflictService.create(dto);
    }

    @PutMapping("{id}")
    public ConflictDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") long conflictId,
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody ConflictUpdateRequestDTO dto) {
        dto.setLocale(locale);
        dto.setUser(user);
        dto.setConflictId(conflictId);
        return conflictService.update(dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long conflictId) {
        conflictService.delete(conflictId);
    }
}
