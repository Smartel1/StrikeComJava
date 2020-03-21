package ru.smartel.strike.service.client_version;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.client_version.ClientVersionDTO;
import ru.smartel.strike.entity.ClientVersion;
import ru.smartel.strike.exception.ValidationException;
import ru.smartel.strike.repository.client_version.ClientVersionRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVersionService {
    private ClientVersionDTOValidator clientVersionDTOValidator;
    private ClientVersionRepository clientVersionRepository;

    public ClientVersionService(ClientVersionDTOValidator clientVersionDTOValidator,
                                ClientVersionRepository clientVersionRepository) {
        this.clientVersionDTOValidator = clientVersionDTOValidator;
        this.clientVersionRepository = clientVersionRepository;
    }

    public ListWrapperDTO<ClientVersionDTO> getNewVersions(ClientVersionGetNewRequestDTO dto) {
        clientVersionDTOValidator.validateListRequestDTO(dto);

        List<ClientVersion> clientVersions;

        if (dto.getCurrentVersion() != null) {
            ClientVersion currentVersion = clientVersionRepository.getByVersionAndClientId(dto.getCurrentVersion(), dto.getClientId())
                    .orElseThrow(() -> new ValidationException(
                            Collections.singletonMap("error", Collections.singletonList("Нет такой версии"))));

            clientVersions = clientVersionRepository.findAllByIdGreaterThanAndClientId(
                    currentVersion.getId(), dto.getClientId());
        } else {
            clientVersions = clientVersionRepository.findAllByClientId(dto.getClientId());
        }

        List<ClientVersionDTO> newVersions = clientVersions.stream()
                .map(cv -> ClientVersionDTO.of(cv, dto.getLocale()))
                .collect(Collectors.toList());

        return new ListWrapperDTO<>(
                newVersions,
                null
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ClientVersionDTO create(ClientVersionCreateRequestDTO dto) {
        clientVersionDTOValidator.validateStoreDTO(dto);

        Optional<ClientVersion> sameVersion = clientVersionRepository.getByVersionAndClientId(dto.getVersion(), dto.getClientId());

        if (sameVersion.isPresent()) {
            throw new ValidationException(
                    Collections.singletonMap("error", Collections.singletonList("Такая версия уже существует")));
        }

        ClientVersion version = new ClientVersion();
        version.setClientId(dto.getClientId());
        version.setRequired(dto.isRequired());
        version.setVersion(dto.getVersion());
        version.setDescriptionRu(dto.getDescriptionRu());
        version.setDescriptionEn(dto.getDescriptionEn());
        version.setDescriptionEs(dto.getDescriptionEs());
        version.setDescriptionDe(dto.getDescriptionDe());

        clientVersionRepository.save(version);

        return ClientVersionDTO.of(version, dto.getLocale());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void delete(long clientVersionId) {
        ClientVersion clientVersion = clientVersionRepository.findById(clientVersionId).orElseThrow(
                () -> new EntityNotFoundException("Версия клиента не найдена"));

        clientVersionRepository.delete(clientVersion);
    }
}
