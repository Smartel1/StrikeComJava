package ru.smartel.strike.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @Value("${apiUrl}")
    private String apiUrl;

    @GetMapping("moderation")
    public String moderation(Model model) {
        model.addAttribute("apiUrl", apiUrl);
        return "moderation";
    }
}
