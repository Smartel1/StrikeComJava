package ru.smartel.strike.service.client_version;

import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface ClientVersionDTOValidator {
    void validateListRequestDTO(ClientVersionGetNewRequestDTO dto) throws DTOValidationException;
    void validateStoreDTO(ClientVersionCreateRequestDTO dto) throws DTOValidationException;
}
