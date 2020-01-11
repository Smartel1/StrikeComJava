package ru.smartel.strike.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;
import ru.smartel.strike.dto.response.DetailWrapperDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.locality.LocalityDetailDTO;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.locality.LocalityService;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/v2/{locale}/localities")
@Validated
public class LocalityController {

    private LocalityService localityService;

    public LocalityController(LocalityService localityService) {
        this.localityService = localityService;
    }

    @GetMapping
    public ListWrapperDTO<LocalityDetailDTO> list(
            @PathVariable("locale") Locale locale,
            @RequestParam(value = "name", required = false) @Size(min = 2) String name,
            @RequestParam(value = "regionId", required = false) Integer regionId) {
        return localityService.list(name, regionId, locale);
    }

    @PostMapping(consumes = {"application/json"})
    public DetailWrapperDTO<LocalityDetailDTO> create(
            @PathVariable("locale") Locale locale,
            @RequestBody LocalityCreateRequestDTO dto) {
        dto.setLocale(locale);
        return new DetailWrapperDTO<>(localityService.create(dto));
    }
}
