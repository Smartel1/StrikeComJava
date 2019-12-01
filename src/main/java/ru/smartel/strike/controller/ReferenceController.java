package ru.smartel.strike.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.response.DetailWrapperDTO;
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

    @GetMapping("references")
    public DetailWrapperDTO<Map<String, List<?>>> list(@PathVariable("locale") Locale locale) {
        return new DetailWrapperDTO<>(referenceService.getAllReferences(locale));
    }

    @GetMapping("references-checksum")
    public DetailWrapperDTO<ReferenceChecksumDTO> checksum() {
        return new DetailWrapperDTO<>(new ReferenceChecksumDTO(referenceService.getChecksum()));
    }
}
