package ru.smartel.strike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTOCreator;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.JsonSchemaValidationException;
import ru.smartel.strike.model.Event;
import ru.smartel.strike.model.User;
import ru.smartel.strike.resolver.EntityPathVariable;
import ru.smartel.strike.service.EventService;
import ru.smartel.strike.service.JsonSchemaValidator;
import ru.smartel.strike.service.Locale;

import java.io.IOException;

@RestController
@RequestMapping("/api/{locale}/event")
@Transactional(rollbackFor = Exception.class)
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
    ) throws JsonProcessingException {

        return "method needs to be implemented";
    }

    @GetMapping("{id}")
    public EventDetailDTO show(
            @PathVariable("locale") Locale locale,
            @EntityPathVariable("id") Event event,
            @RequestParam(value = "with_relatives", required = false) boolean withRelatives
    ) {
        eventService.incrementViews(event);

        return EventDetailDTOCreator.create(event, locale, withRelatives);
    }

    @PostMapping(consumes = {"application/json"})
    public EventDetailDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody JsonNode data,
            @AuthenticationPrincipal User user
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/store");

        Event event = eventService.create(data, user, locale);

        return EventDetailDTOCreator.create(event, locale, false);
    }

    @PutMapping(path = "{id}", consumes = {"application/json"})
    @Transactional(rollbackFor = Exception.class)
    public JsonNode update(
            @PathVariable("locale") Locale locale,
            @EntityPathVariable("id") Event event,
            @AuthenticationPrincipal User user,
            @RequestBody JsonNode data
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/store");

        eventService.update(event, data, user, locale);

        return data;
    }
}
