package ru.smartel.strike.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.client_version.ClientVersionDTO;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.client_version.ClientVersionService;

@RestController
@RequestMapping("/api/v2/{locale}/client-versions")
public class ClientVersionController {

    private ClientVersionService clientVersionService;

    public ClientVersionController(ClientVersionService clientVersionService) {
        this.clientVersionService = clientVersionService;
    }

    @GetMapping
    public ListWrapperDTO<ClientVersionDTO> getNewVersions(ClientVersionGetNewRequestDTO dto) {
        return clientVersionService.getNewVersions(dto);
    }

    @PostMapping(consumes = {"application/json"})
    public ClientVersionDTO store(
            @PathVariable("locale") Locale locale,
            @RequestBody ClientVersionCreateRequestDTO dto) {
        dto.setLocale(locale);
        return clientVersionService.create(dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") int clientVersionId) {
        clientVersionService.delete(clientVersionId);
    }
}
