package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListWrapperDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.EventService;
import ru.smartel.strike.service.JsonSchemaValidator;
import ru.smartel.strike.service.Locale;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/{locale}")
@Validated
public class EventController {

    private JsonSchemaValidator validator;
    private EventService eventService;

    public EventController(JsonSchemaValidator validator, EventService eventService) {
        this.validator = validator;
        this.eventService = eventService;
    }

    @GetMapping("/event")
    public EventListWrapperDTO index(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestBody EventListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        return eventService.list(dto, perPage, page, locale, user);
    }

    @PostMapping("/event-list")
    public EventListWrapperDTO postIndex(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestBody EventListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        //alias of index method
        return index(locale, perPage, page, dto, user);
    }

    @GetMapping("/event/{id}")
    public EventDetailDTO show(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int eventId,
            @RequestParam(value = "with_relatives", required = false) boolean withRelatives
    ) {
        return eventService.incrementViewsAndGet(eventId, locale, withRelatives);
    }

    @PostMapping("/event/{id}/favourite")
    public void setFavourite(
            @PathVariable("id") int eventId,
            @RequestParam(value = "favourite") boolean isFavourite,
            @AuthenticationPrincipal User user
    ) {
        eventService.setFavourite(eventId, user.getId(), isFavourite);
    }

    @PostMapping(path = "/event/", consumes = {"application/json"})
    public EventDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody EventRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws BusinessRuleValidationException, DTOValidationException {
        return eventService.create(dto, user.getId(), locale);
    }

    @PutMapping(path = "/event/{id}", consumes = {"application/json"})
    public EventDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int eventId,
            @AuthenticationPrincipal User user,
            @RequestBody EventRequestDTO dto
    ) throws BusinessRuleValidationException, DTOValidationException {
        return eventService.update(eventId, dto, user.getId(), locale);
    }

    @DeleteMapping(path = "/event/{id}")
    public void delete(
            @PathVariable("id") int eventId
    ) {
        eventService.delete(eventId);
    }
}
