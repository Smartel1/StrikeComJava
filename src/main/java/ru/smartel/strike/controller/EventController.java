package ru.smartel.strike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.EventStoreDTO;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.JsonSchemaValidationException;
import ru.smartel.strike.model.Event;
import ru.smartel.strike.model.User;
import ru.smartel.strike.service.EventService;
import ru.smartel.strike.service.JsonSchemaValidator;
import ru.smartel.strike.service.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
            @AuthenticationPrincipal User user,
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
            HttpServletRequest request,
            @PathVariable("locale") String locale,
            @PathVariable("id") Integer id
    ) throws JsonProcessingException {
        return "method needs to be implemented";
    }

    @PostMapping(consumes = {"application/json"})
    public JsonNode store(
            HttpServletRequest request,
            @RequestAttribute Locale locale,
            @RequestBody JsonNode data
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/store");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal().getClass());
//        eventService.create(data, user, locale);

        return data;
    }

    @PutMapping(path = "{id}", consumes = {"application/json"})
    public JsonNode update(
            HttpServletRequest request,
            @RequestAttribute Locale locale,
            @PathVariable("id") int eventId,
            @RequestBody JsonNode data
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/store");

//        eventService.update(eventId, data, user, locale);

        return data;
    }
}
