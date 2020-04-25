package ru.smartel.strike.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.event.*;
import ru.smartel.strike.dto.response.DetailWrapperDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.event.EventService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v2/{locale}/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ListWrapperDTO<EventListDTO> index(
            EventListRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        dto.setUser(user);
        return eventService.list(dto);
    }

    @GetMapping("{id}")
    public DetailWrapperDTO<EventDetailDTO> show(EventShowDetailRequestDTO dto) {
        return new DetailWrapperDTO<>(eventService.incrementViewsAndGet(dto));
    }

    @PostMapping("{id}/favourites")
    public void setFavourite(
            @PathVariable("id") long eventId,
            @RequestBody EventFavouritesRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal user) {
        Optional.ofNullable(user).ifPresent(usr -> eventService.setFavourite(eventId, usr.getId(), dto.isFavourite()));
    }

    @PostMapping
    public DetailWrapperDTO<EventDetailDTO> store(
            @PathVariable("locale") Locale locale,
            @RequestBody EventCreateRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal user) {
        dto.setLocale(locale);
        dto.setUser(user);
        return new DetailWrapperDTO<>(eventService.create(dto));
    }

    @PutMapping("{id}")
    public DetailWrapperDTO<EventDetailDTO> update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") long eventId,
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody EventUpdateRequestDTO dto) {
        dto.setEventId(eventId);
        dto.setLocale(locale);
        dto.setUser(user);
        return new DetailWrapperDTO<>(eventService.update(dto));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long eventId) {
        eventService.delete(eventId);
    }
}
