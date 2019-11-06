package ru.smartel.strike.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.region.RegionCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.region.RegionDetailDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.region.RegionService;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/v2/{locale}/region")
@Validated
public class RegionController {

    private RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ListWrapperDTO<RegionDetailDTO> list (
            @PathVariable("locale") Locale locale,
            @RequestParam(value = "name") @Size(min = 2) String name,
            @RequestParam(value = "countryId", required = false) Integer countryId
    ) {
        return regionService.list(name, countryId, locale);
    }

    @PostMapping
    public RegionDetailDTO create (
            @PathVariable("locale") Locale locale,
            @RequestBody RegionCreateRequestDTO dto
    ) throws DTOValidationException {
        dto.setLocale(locale);
        return regionService.create(dto);
    }
}
