package ru.smartel.strike.service.client_version;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.client_version.ClientVersionDTO;
import ru.smartel.strike.entity.ClientVersion;
import ru.smartel.strike.exception.ValidationException;
import ru.smartel.strike.repository.client_version.ClientVersionRepository;
import ru.smartel.strike.service.Locale;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientVersionServiceTest {

    @InjectMocks
    private ClientVersionService service;

    @Mock
    private ClientVersionRepository repository;

    @Mock
    private ClientVersionDTOValidator dtoValidator;

    private ClientVersionCreateRequestDTO requestDto;

    private ClientVersionDTO clientVersionDTO;

    private final long ID = 1;
    private final String VERSION = "version";
    private final String DESCRIPTION_DE = "descriptionDe";
    private final String DESCRIPTION_RU = "descriptionRu";
    private final String DESCRIPTION_EN = "descriptionEn";
    private final String DESCRIPTION_ES = "descriptionEs";

    @BeforeEach
    void setUp() {
        // todo: (убрать после ревью) - насколько критично здесь инициализировать объекты, которые не для всех тестов общие (для меня не критично)
        requestDto = new ClientVersionCreateRequestDTO();
        requestDto.setClientId("id");
        requestDto.setDescriptionDe(DESCRIPTION_DE);
        requestDto.setDescriptionEn(DESCRIPTION_EN);
        requestDto.setDescriptionEs(DESCRIPTION_ES);
        requestDto.setDescriptionRu(DESCRIPTION_RU);
        requestDto.setLocale(Locale.RU);
        requestDto.setVersion(VERSION);
        requestDto.setRequired(false);

        clientVersionDTO = new ClientVersionDTO();
        clientVersionDTO.setId(ID);
        clientVersionDTO.setRequired(true);
        clientVersionDTO.setVersion(VERSION);
    }

    // Проверка на выкидывание ValidationException
    @Test
    void whenGetNewVersionsShouldThrowValidationException() {
        var getNewRequestDto = new ClientVersionGetNewRequestDTO();
        getNewRequestDto.setClientId(VERSION);
        getNewRequestDto.setCurrentVersion("CURRENT_" + VERSION);
        getNewRequestDto.setLocale(Locale.RU);

        when(repository.getByVersionAndClientId(any(String.class), any(String.class))).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> service.getNewVersions(getNewRequestDto));
    }

    @Test
    void whenGetNewVersionsShouldReturnListWrapperDto() {
        var getNewRequestDto = new ClientVersionGetNewRequestDTO();
        getNewRequestDto.setClientId(VERSION);
        getNewRequestDto.setLocale(Locale.RU);

        var clientVersion = new ClientVersion();
        clientVersion.setVersion(VERSION);

        when(repository.findAllByClientId(any(String.class))).thenReturn(Arrays.asList(clientVersion));

        ListWrapperDTO<ClientVersionDTO> versions = service.getNewVersions(getNewRequestDto);

        assertSame(VERSION, versions.getData().get(0).getVersion());
    }

    // Проверка кейса с Locale.RU
    @Test
    void whenCreateAndSameVersionIsNotPresetItShouldReturnClientVersionDTO() {
        when(repository.getByVersionAndClientId(any(String.class), any(String.class)))
                .thenReturn(Optional.empty());

        when(repository.save(any(ClientVersion.class))).thenReturn(new ClientVersion());

        ClientVersionDTO version = service.create(requestDto);

        assertSame(requestDto.getVersion(), version.getVersion());
        assertSame(requestDto.getDescriptionRu(), version.getOptionalFields().get("description"));

        assertNull(version.getOptionalFields().get(DESCRIPTION_RU));
        assertNull(version.getOptionalFields().get(DESCRIPTION_DE));
        assertNull(version.getOptionalFields().get(DESCRIPTION_EN));
        assertNull(version.getOptionalFields().get(DESCRIPTION_RU));
    }

    // Проверка кейса с Locale.ALL
    @Test
    void whenCreateAndSameVersionIsNotPresetAndLocaleIsAllItShouldReturnClientVersionDTO() {
        requestDto.setLocale(Locale.ALL);

        ClientVersionDTO version = service.create(requestDto);

        assertSame(requestDto.getDescriptionRu(), version.getOptionalFields().get(DESCRIPTION_RU));
        assertSame(requestDto.getDescriptionDe(), version.getOptionalFields().get(DESCRIPTION_DE));
        assertSame(requestDto.getDescriptionEn(), version.getOptionalFields().get(DESCRIPTION_EN));
        assertSame(requestDto.getDescriptionRu(), version.getOptionalFields().get(DESCRIPTION_RU));

        assertNull(version.getOptionalFields().get("description"));
    }

    // Проверка на выкидывание ValidationException
    @Test
    void whenCreateAndSameVersionIsPresetItShouldThrowValidationException() {
        when(repository.getByVersionAndClientId(any(String.class), any(String.class)))
                .thenReturn(java.util.Optional.of(new ClientVersion()));

        assertThrows(ValidationException.class, () -> service.create(requestDto));
    }

    // Проверка на выкидывание EntityNotFoundException
    @Test
    void whenDeleteIsAbsentClientShouldThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> service.delete(ID));
    }

}