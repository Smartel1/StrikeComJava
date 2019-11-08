package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.dto.request.event.EventUpdateRequestDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.event.EventService;
import ru.smartel.strike.service.Locale;

import java.util.Optional;

@RestController
@RequestMapping("/api/v2/{locale}")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event")
    public ListWrapperDTO<EventListDTO> index(
            EventListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws DTOValidationException {
        dto.setUser(user);
        return eventService.list(dto);
    }

    @GetMapping("/event/{id}")
    public EventDetailDTO show(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int eventId,
            @RequestParam(value = "withRelatives", required = false) boolean withRelatives
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

    @PostMapping(path = "/event")
    public EventDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody EventCreateRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws BusinessRuleValidationException, DTOValidationException {
        dto.setLocale(locale);
        dto.setUser(user);
        return eventService.create(dto);
    }

    @PutMapping(path = "/event/{id}")
    public EventDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int eventId,
            @AuthenticationPrincipal User user,
            @RequestBody EventUpdateRequestDTO dto
    ) throws BusinessRuleValidationException, DTOValidationException {
        dto.setEventId(eventId);
        dto.setLocale(locale);
        dto.setUser(user);
        return eventService.update(dto);
    }

    @DeleteMapping(path = "/event/{id}")
    public void delete(
            @PathVariable("id") int eventId
    ) throws BusinessRuleValidationException {
        eventService.delete(eventId);
    }
}
