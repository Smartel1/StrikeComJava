package ru.smartel.strike.service.client_version;

import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.client_version.ClientVersionDTO;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;


public interface ClientVersionService {
    ListWrapperDTO<ClientVersionDTO> getNewVersions(ClientVersionGetNewRequestDTO dto, Locale locale) throws BusinessRuleValidationException, DTOValidationException;
    ClientVersionDTO create(ClientVersionCreateRequestDTO dto, Locale locale) throws BusinessRuleValidationException, DTOValidationException;
    void delete(int clientVersionId);
}
