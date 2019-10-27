package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.EventService;
import ru.smartel.strike.service.Locale;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/{locale}")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event")
    public ListWrapperDTO<EventListDTO> index(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestBody EventListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        dto.mergeWith(page, perPage);
        return eventService.list(dto, locale, user);
    }

    @PostMapping("/event-list")
    public ListWrapperDTO postIndex(
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
        Optional.ofNullable(user).ifPresent(
                usr -> eventService.setFavourite(eventId, usr.getId(), isFavourite)
        );
    }

    @PostMapping(path = "/event", consumes = {"application/json"})
    public EventDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody EventRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws BusinessRuleValidationException, DTOValidationException {
        return eventService.create(dto, null != user? user.getId() : null, locale);
    }

    @PutMapping(path = "/event/{id}", consumes = {"application/json"})
    public EventDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int eventId,
            @AuthenticationPrincipal User user,
            @RequestBody EventRequestDTO dto
    ) throws BusinessRuleValidationException, DTOValidationException {
        return eventService.update(eventId, dto, null != user? user.getId() : null, locale);
    }

    @DeleteMapping(path = "/event/{id}")
    public void delete(
            @PathVariable("id") int eventId
    ) throws BusinessRuleValidationException {
        eventService.delete(eventId);
    }
}
