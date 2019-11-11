package ru.smartel.strike.service.client_version;

import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.client_version.ClientVersionDTO;


public interface ClientVersionService {
    ListWrapperDTO<ClientVersionDTO> getNewVersions(ClientVersionGetNewRequestDTO dto);
    ClientVersionDTO create(ClientVersionCreateRequestDTO dto);
    void delete(long clientVersionId);
}
