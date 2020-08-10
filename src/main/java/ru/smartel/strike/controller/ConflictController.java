package ru.smartel.strike.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;
import ru.smartel.strike.dto.response.DetailWrapperDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.conflict.ConflictListDTO;
import ru.smartel.strike.dto.response.conflict.ConflictReportDTO;
import ru.smartel.strike.dto.response.reference.locality.ExtendedLocalityDTO;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.conflict.ConflictService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v2/{locale}/conflicts")
public class ConflictController {

    private final ConflictService conflictService;

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
    public DetailWrapperDTO<ConflictDetailDTO> show(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") long conflictId) {
        return new DetailWrapperDTO<>(conflictService.get(conflictId, locale));
    }

    @GetMapping("report")
    public DetailWrapperDTO<ConflictReportDTO> report(@RequestParam("from")
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                      @RequestParam("to")
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return new DetailWrapperDTO<>(conflictService.getReportByPeriod(from, to));
    }

    @GetMapping("{id}/latest-locality")
    public DetailWrapperDTO<ExtendedLocalityDTO> latestCoordinates(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") long conflictId) {
        return new DetailWrapperDTO<>(conflictService.getLatestLocality(conflictId, locale));
    }

    @PostMapping
    public DetailWrapperDTO<ConflictDetailDTO> store(
            @PathVariable("locale") Locale locale,
            @RequestBody ConflictCreateRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal user) {
        dto.setLocale(locale);
        dto.setUser(user);
        return new DetailWrapperDTO<>(conflictService.create(dto));
    }

    @PutMapping("{id}")
    public DetailWrapperDTO<ConflictDetailDTO> update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") long conflictId,
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody ConflictUpdateRequestDTO dto) {
        dto.setLocale(locale);
        dto.setUser(user);
        dto.setConflictId(conflictId);
        return new DetailWrapperDTO<>(conflictService.update(dto));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long conflictId) {
        conflictService.delete(conflictId);
    }
}
