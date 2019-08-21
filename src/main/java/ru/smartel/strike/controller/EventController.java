package ru.smartel.strike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.model.Event;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/{locale}/event")
public class EventController {

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
            HttpServletRequest request,
            @PathVariable("locale") String locale,
            @PathVariable("id") Integer id
    ) throws JsonProcessingException {
        return "method needs to be implemented";
    }

    @PostMapping()
    public String store(HttpServletRequest request, @Valid @RequestBody Event event) {
        return "method needs to be implemented";
    }
}
