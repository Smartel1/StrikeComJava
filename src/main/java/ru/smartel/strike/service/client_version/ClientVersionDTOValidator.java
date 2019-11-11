package ru.smartel.strike.service.client_version;

import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;

public interface ClientVersionDTOValidator {
    void validateListRequestDTO(ClientVersionGetNewRequestDTO dto);
    void validateStoreDTO(ClientVersionCreateRequestDTO dto);
}
