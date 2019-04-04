package ru.smartel.strike.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.jsonView.View;
import ru.smartel.strike.model.Event;
import ru.smartel.strike.repository.EventRepository;

import java.util.List;

@RestController
public class Controller {
    private EventRepository eventRepository;

    public Controller(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/api/{locale}/event/{id}")
    @JsonView(View.Detail.class)
    public Event hello(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        System.out.println(locale);
        return eventRepository.find(id);
    }

    @GetMapping("/api/{locale}/event/")
    @JsonView(View.List.class)
    public List<Event> hello(@PathVariable("locale") String locale) {
        return eventRepository.get(5);
    }
}
