package ru.smartel.strike.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;
import ru.smartel.strike.dto.response.DetailWrapperDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.country.CountryDTO;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.country.CountryService;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/v2/{locale}/countries")
@Validated
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ListWrapperDTO<CountryDTO> list (
            @PathVariable("locale") Locale locale,
            @RequestParam(value = "name", required = false) @Size(min = 2) String name) {
        return countryService.list(name, locale);
    }

    @PostMapping
    public DetailWrapperDTO<CountryDTO> create (
            @PathVariable("locale") Locale locale,
            @RequestBody CountryCreateRequestDTO dto) {
        dto.setLocale(locale);
        return new DetailWrapperDTO<>(countryService.create(dto));
    }
}
