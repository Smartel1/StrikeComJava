package ru.smartel.strike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("moderation")
    public String moderation() {
        return "moderation";
    }
}
