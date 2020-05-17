package ru.smartel.strike.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.dto.response.DetailWrapperDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.client_version.ClientVersionDTO;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.client_version.ClientVersionService;

@RestController
@RequestMapping("/api/v2/{locale}/client-versions")
public class ClientVersionController {

    private final ClientVersionService clientVersionService;

    public ClientVersionController(ClientVersionService clientVersionService) {
        this.clientVersionService = clientVersionService;
    }

    @ApiOperation(value = "Получить список новых версий")
    @GetMapping
    public ListWrapperDTO<ClientVersionDTO> getNewVersions(ClientVersionGetNewRequestDTO dto) {
        return clientVersionService.getNewVersions(dto);
    }

    @ApiOperation(value = "Создать запись о версии приложения. clientId - уникальный идентификатор для приложения")
    @PostMapping(consumes = {"application/json"})
    public DetailWrapperDTO<ClientVersionDTO> store(
            @PathVariable("locale") Locale locale,
            @RequestBody ClientVersionCreateRequestDTO dto) {
        dto.setLocale(locale);
        return new DetailWrapperDTO<>(clientVersionService.create(dto));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") int clientVersionId) {
        clientVersionService.delete(clientVersionId);
    }
}
