package ru.smartel.strike.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.EventListRequestDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.dto.response.event.EventListWrapperDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.JsonSchemaValidationException;
import ru.smartel.strike.service.EventService;
import ru.smartel.strike.service.JsonSchemaValidator;
import ru.smartel.strike.service.Locale;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
            @RequestBody EventListRequestDTO data,
            @AuthenticationPrincipal User user
    ) {
        return eventService.index(
                data.getFilters(),
                perPage,
                page,
                locale,
                user
        );
    }

    @PostMapping("/event-list")
    public EventListWrapperDTO postIndex(
            @PathVariable("locale") Locale locale,
            @RequestParam(name = "per_page", required = false, defaultValue = "20") @Min(1) Integer perPage,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestBody EventListRequestDTO data,
            @AuthenticationPrincipal User user
    ) {
        //alias of index method
        return index(locale, perPage, page, data, user);
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
            @RequestBody JsonNode data,
            @AuthenticationPrincipal User user
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/store");

        return eventService.create(data, user.getId(), locale);
    }

    @PutMapping(path = "/event/{id}", consumes = {"application/json"})
    public EventDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int eventId,
            @AuthenticationPrincipal User user,
            @RequestBody JsonNode data
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/update");

        return eventService.update(eventId, data, user.getId(), locale);
    }

    @DeleteMapping(path = "/event/{id}")
    public void delete(
            @PathVariable("id") int eventId
    ) {
        eventService.delete(eventId);
    }
}
