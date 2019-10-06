package ru.smartel.strike.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.JsonSchemaValidationException;
import ru.smartel.strike.service.impl.EventServiceImpl;
import ru.smartel.strike.service.JsonSchemaValidator;
import ru.smartel.strike.service.Locale;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api1/{locale}/test")
public class TestController {

    private JsonSchemaValidator validator;
    private EventServiceImpl eventService;

    public TestController(JsonSchemaValidator validator, EventServiceImpl eventService) {
        this.validator = validator;
        this.eventService = eventService;
    }

    @PostMapping(consumes = {"application/json"})
    public JsonNode index(
            HttpServletRequest request,
            @RequestAttribute Locale locale,
            @RequestBody JsonNode data
    ) throws JsonSchemaValidationException, IOException, ProcessingException, BusinessRuleValidationException {

        validator.validate(data, "event/store");

//        eventService.create(data, user, locale);

        return data;
    }
}
