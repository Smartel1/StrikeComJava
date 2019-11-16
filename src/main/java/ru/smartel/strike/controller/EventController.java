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
import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventShowDetailRequestDTO;
import ru.smartel.strike.dto.request.event.EventUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.ValidationException;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.event.EventService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v2/{locale}/events")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ListWrapperDTO<EventListDTO> index(
            EventListRequestDTO dto,
            @AuthenticationPrincipal User user
    ) throws ValidationException {
        dto.setUser(user);
        return eventService.list(dto);
    }

    @GetMapping("{id}")
    public EventDetailDTO show(EventShowDetailRequestDTO dto) {
        return eventService.incrementViewsAndGet(dto);
    }

    @PostMapping("{id}/favourites")
    public void setFavourite(
            @PathVariable("id") long eventId,
            @RequestParam(value = "favourite") boolean isFavourite,
            @AuthenticationPrincipal User user) {
        Optional.ofNullable(user).ifPresent(
                usr -> eventService.setFavourite(eventId, usr.getId(), isFavourite));
    }

    @PostMapping
    public EventDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody EventCreateRequestDTO dto,
            @AuthenticationPrincipal User user) {
        dto.setLocale(locale);
        dto.setUser(user);
        return eventService.create(dto);
    }

    @PutMapping("{id}")
    public EventDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") long eventId,
            @AuthenticationPrincipal User user,
            @RequestBody EventUpdateRequestDTO dto) {
        dto.setEventId(eventId);
        dto.setLocale(locale);
        dto.setUser(user);
        return eventService.update(dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long eventId) {
        eventService.delete(eventId);
    }
}
