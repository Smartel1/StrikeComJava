package ru.smartel.strike.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.jsonSerializer.EventListSerializer;
import ru.smartel.strike.model.Event;
import ru.smartel.strike.repository.EventRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/{locale}/event/")
public class Controller {

    private EventRepository eventRepository;

    public Controller(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @PathVariable("locale") String locale) throws JsonProcessingException {
        List<Event> events = eventRepository.get(5);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();

        EventListSerializer serializer = new EventListSerializer();
        serializer.setLang(locale);
        simpleModule.addSerializer(Event.class, serializer);

        objectMapper.registerModule(simpleModule);
        return objectMapper.writeValueAsString(events);
    }

    @GetMapping("{id}")
    public Event show(HttpServletRequest request,
                      @PathVariable("locale") String locale,
                      @PathVariable("id") Integer id) throws JsonProcessingException {
        Event event = eventRepository.find(id);
        return event;
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule simpleModule = new SimpleModule();
//
//        EventDetailSerializer serializer = new EventDetailSerializer();
//        serializer.setLang(locale);
//        simpleModule.addSerializer(Event.class, serializer);
//
//        objectMapper.registerModule(simpleModule);
//        return objectMapper.writeValueAsString(event);
    }
}
