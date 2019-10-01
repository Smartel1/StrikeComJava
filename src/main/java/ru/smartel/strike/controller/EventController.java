package ru.smartel.strike.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.JsonSchemaValidationException;
import ru.smartel.strike.service.EventService;
import ru.smartel.strike.service.JsonSchemaValidator;
import ru.smartel.strike.service.Locale;

import java.io.IOException;

@RestController
@RequestMapping("/api/{locale}/event")
public class EventController {

    private JsonSchemaValidator validator;
    private EventService eventService;

    public EventController(JsonSchemaValidator validator, EventService eventService) {
        this.validator = validator;
        this.eventService = eventService;
    }

    @GetMapping()
    public String index(
            @PathVariable("locale") Locale locale,
            @RequestParam(
                    name = "per_page",
                    required = false,
                    defaultValue = "20"
            ) Integer perPage
    ) {
        return "method needs to be implemented";
    }

    @GetMapping("{id}")
    public EventDetailDTO show(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int eventId,
            @RequestParam(value = "with_relatives", required = false) boolean withRelatives
    ) {
        return eventService.getAndIncrementViews(eventId, locale, withRelatives);
    }

    @PostMapping(consumes = {"application/json"})
    public EventDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody JsonNode data,
            @AuthenticationPrincipal User user
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/store");

        return eventService.create(data, user, locale);
    }

    @PutMapping(path = "{id}", consumes = {"application/json"})
    public EventDetailDTO update(
            @PathVariable("locale") Locale locale,
            @PathVariable("id") int eventId,
            @AuthenticationPrincipal User user,
            @RequestBody JsonNode data
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/update");

        return eventService.update(eventId, data, user, locale);
    }

    @DeleteMapping(path = "{id}")
    public void delete(
            @PathVariable("id") int eventId
    ) {
        eventService.delete(eventId);
    }
}
