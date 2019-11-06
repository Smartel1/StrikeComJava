package ru.smartel.strike.controller;

import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.response.reference.ReferenceChecksumDTO;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.reference.ReferenceService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/{locale}")
public class ReferenceController {
    private ReferenceService referenceService;

    public ReferenceController(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @GetMapping("reference")
    public Map<String, List<?>> list(
            @PathVariable("locale") Locale locale
    ) {
        return referenceService.getAllReferences(locale);
    }

    @GetMapping("reference-checksum")
    public ReferenceChecksumDTO checksum() {
        return new ReferenceChecksumDTO(
                referenceService.getChecksum()
        );
    }
}
