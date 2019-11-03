package ru.smartel.strike.service.client_version;

import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.client_version.ClientVersionDTO;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;


public interface ClientVersionService {
    ListWrapperDTO<ClientVersionDTO> getNewVersions(ClientVersionGetNewRequestDTO dto) throws BusinessRuleValidationException, DTOValidationException;
    ClientVersionDTO create(ClientVersionCreateRequestDTO dto) throws BusinessRuleValidationException, DTOValidationException;
    void delete(int clientVersionId);
}
