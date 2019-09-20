package ru.smartel.strike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.JsonSchemaValidationException;
import ru.smartel.strike.model.User;
import ru.smartel.strike.service.EventService;
import ru.smartel.strike.service.JsonSchemaValidator;
import ru.smartel.strike.service.Locale;

import javax.servlet.http.HttpServletRequest;
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
            HttpServletRequest request,
            @PathVariable("locale") String locale,
            @RequestParam(
                    name = "per_page",
                    required = false,
                    defaultValue = "20"
            ) Integer perPage
    ) throws JsonProcessingException {

        return "method needs to be implemented";
    }

    @GetMapping("{id}")
    public String show(
            @PathVariable("locale") String locale,
            @PathVariable("id") Integer id
    ) {
        return "method needs to be implemented";
    }

    @PostMapping(consumes = {"application/json"})
    public JsonNode store(
            @RequestAttribute Locale locale,
            @AuthenticationPrincipal User user,
            @RequestBody JsonNode data
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/store");

        eventService.create(data, user, locale);

        return data;
    }

    @PutMapping(path = "{id}", consumes = {"application/json"})
    public JsonNode update(
            @RequestAttribute Locale locale,
            @AuthenticationPrincipal User user,
            @PathVariable("id") int eventId,
            @RequestBody JsonNode data
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/store");

        eventService.update(eventId, data, user, locale);

        return data;
    }
}
