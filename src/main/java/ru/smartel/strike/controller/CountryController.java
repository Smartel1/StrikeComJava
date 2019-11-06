package ru.smartel.strike.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.country.CountryDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.country.CountryService;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/v2/{locale}/country")
@Validated
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ListWrapperDTO<CountryDTO> list (
            @PathVariable("locale") Locale locale,
            @RequestParam(value = "name") @Size(min = 2) String name
    ) {
        return countryService.list(name, locale);
    }

    @PostMapping
    public CountryDTO create (
            @PathVariable("locale") Locale locale,
            @RequestBody CountryCreateRequestDTO dto
    ) throws DTOValidationException {
        dto.setLocale(locale);
        return countryService.create(dto);
    }
}
