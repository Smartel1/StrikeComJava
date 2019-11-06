package ru.smartel.strike.controller;

import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.client_version.ClientVersionDTO;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.client_version.ClientVersionService;
import ru.smartel.strike.service.Locale;

@RestController
@RequestMapping("/api/v2/{locale}")
public class ClientVersionController {

    private ClientVersionService clientVersionService;

    public ClientVersionController(ClientVersionService clientVersionService) {
        this.clientVersionService = clientVersionService;
    }

    @GetMapping("/client-version")
    public ListWrapperDTO<ClientVersionDTO> getNewVersions(
            ClientVersionGetNewRequestDTO dto
    ) throws DTOValidationException, BusinessRuleValidationException {
        return clientVersionService.getNewVersions(dto);
    }

    @PostMapping(path = "/client-version", consumes = {"application/json"})
    public ClientVersionDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody ClientVersionCreateRequestDTO dto
    ) throws BusinessRuleValidationException, DTOValidationException {
        dto.setLocale(locale);
        return clientVersionService.create(dto);
    }

    @DeleteMapping(path = "/client-version/{id}")
    public void delete(
            @PathVariable("id") int clientVersionId
    ) {
        clientVersionService.delete(clientVersionId);
    }
}
