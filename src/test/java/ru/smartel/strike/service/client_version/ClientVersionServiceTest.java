package ru.smartel.strike.service.client_version;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.client_version.ClientVersionDTO;
import ru.smartel.strike.entity.ClientVersion;
import ru.smartel.strike.exception.ValidationException;
import ru.smartel.strike.mapper.ClientVersionMapper;
import ru.smartel.strike.repository.client_version.ClientVersionRepository;
import ru.smartel.strike.service.Locale;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientVersionServiceTest {

    @InjectMocks
    private ClientVersionService service;

    @Mock
    private ClientVersionRepository repository;

    @Mock
    private ClientVersionDTOValidator dtoValidator;

    private final static long ID = 0;
    private final static String VERSION = "version";
    private final static String DESCRIPTION_DE = "descriptionDe";
    private final static String DESCRIPTION_RU = "descriptionRu";
    private final static String DESCRIPTION_EN = "descriptionEn";
    private final static String DESCRIPTION_ES = "descriptionEs";
    private final static boolean IS_REQUIRED = false;

    private ClientVersionCreateRequestDTO getCreateRequestDto() {
        var requestDTO = new ClientVersionCreateRequestDTO();
        requestDTO.setClientId("id");
        requestDTO.setRequired(IS_REQUIRED);
        requestDTO.setLocale(Locale.RU);
        requestDTO.setVersion(VERSION);
        requestDTO.setDescriptionDe(DESCRIPTION_DE);
        requestDTO.setDescriptionEn(DESCRIPTION_EN);
        requestDTO.setDescriptionEs(DESCRIPTION_ES);
        requestDTO.setDescriptionRu(DESCRIPTION_RU);

        return requestDTO;
    }

    private static ClientVersionDTO getClientVersionDTO() {
        var clientVersionDTO = new ClientVersionDTO();
        clientVersionDTO.setId(ID);
        clientVersionDTO.setVersion(VERSION);
        clientVersionDTO.setRequired(IS_REQUIRED);
        return clientVersionDTO;
    }

    private ClientVersionGetNewRequestDTO clientVersionGetNewRequestDTO() {
        var getNewRequestDto = new ClientVersionGetNewRequestDTO();
        getNewRequestDto.setClientId(VERSION);
        getNewRequestDto.setCurrentVersion("CURRENT_" + VERSION);
        getNewRequestDto.setLocale(Locale.RU);
        return getNewRequestDto;
    }

    // Check for throwing out ValidationException
    @Test
    void whenGetNewVersionsShouldThrowValidationException() {
        var getNewRequestDto = clientVersionGetNewRequestDTO();

        when(repository.getByVersionAndClientId(getNewRequestDto.getCurrentVersion(), getNewRequestDto.getClientId())).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> service.getNewVersions(getNewRequestDto));

        String actualExceptionMessage = exception.getMessage();
        String expectedExceptionMessage = "Validation error occurred";
        assertThat(actualExceptionMessage).isEqualTo(expectedExceptionMessage);

        String actualErrorMessage = exception.getErrors().get("error").get(0);
        String expectedErrorMessage = "Нет такой версии: " + getNewRequestDto.getClientId() + ":" + getNewRequestDto.getCurrentVersion();
        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    @Test
    void whenGetNewVersionsShouldReturnListWrapperDto() {
        var getNewRequestDto = new ClientVersionGetNewRequestDTO();
        getNewRequestDto.setClientId(VERSION);
        getNewRequestDto.setLocale(Locale.RU);

        var clientVersion = new ClientVersion();
        clientVersion.setVersion(VERSION);

        when(repository.findAllByClientId(getNewRequestDto.getClientId())).thenReturn(Arrays.asList(clientVersion));
        ListWrapperDTO<ClientVersionDTO> versions = service.getNewVersions(getNewRequestDto);

        var expected = new ClientVersionDTO();
        expected.setVersion(getNewRequestDto.getClientId());
        expected.add("description", null);

        assertThat(versions.getData()).hasSize(1);
        assertThat(versions.getData()).contains(expected);

        var actual = versions.getData().get(0);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideLocaleForClientVersion")
    void whenCreateAndSameVersionIsNotPresetItShouldReturnClientVersionDTO(Locale locale, ClientVersionDTO expected) {
        var requestDto = getCreateRequestDto();
        requestDto.setLocale(locale);

        when(repository.getByVersionAndClientId(requestDto.getVersion(), requestDto.getClientId())).thenReturn(Optional.empty());
        var clientVersion = ClientVersionMapper.INSTANCE.ClientVersionCreateRequestDTOToClientVersion(requestDto);

        var actual = service.create(requestDto);
        verify(repository).save(clientVersion);

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideLocaleForClientVersion() {
        var expectedForAllLocale = getClientVersionDTO();
        expectedForAllLocale.add(DESCRIPTION_RU, DESCRIPTION_RU);
        expectedForAllLocale.add(DESCRIPTION_EN, DESCRIPTION_EN);
        expectedForAllLocale.add(DESCRIPTION_ES, DESCRIPTION_ES);
        expectedForAllLocale.add(DESCRIPTION_DE, DESCRIPTION_DE);

        var expectedForRuLocale = getClientVersionDTO();
        expectedForRuLocale.add("description", DESCRIPTION_RU);

        return Stream.of(
                Arguments.of(Locale.ALL, expectedForAllLocale),
                Arguments.of(Locale.RU, expectedForRuLocale)
        );
    }

    // Check for throwing out ValidationException
    @Test
    void whenCreateAndSameVersionIsPresetItShouldThrowValidationException() {
        var requestDto = getCreateRequestDto();
        when(repository.getByVersionAndClientId(requestDto.getVersion(), requestDto.getClientId()))
                .thenReturn(Optional.of(new ClientVersion()));

        ValidationException exception = assertThrows(ValidationException.class, () -> service.create(requestDto));

        String actualExceptionMessage = exception.getMessage();
        String expectedExceptionMessage = "Validation error occurred";
        assertThat(actualExceptionMessage).isEqualTo(expectedExceptionMessage);

        String actualErrorMessage = exception.getErrors().get("error").get(0);
        String expectedErrorMessage = "Такая версия уже существует";
        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    // Check for throwing out EntityNotFoundException
    @Test
    void whenDeleteIsAbsentClientShouldThrowEntityNotFoundException() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.delete(ID));

        String actual = exception.getMessage();
        String expected = "Версия клиента не найдена";

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenDeleteShouldVerified() {
        var clientVersion = new ClientVersion();

        when(repository.findById(ID)).thenReturn(Optional.of(clientVersion));

        service.delete(ID);
        verify(repository).delete(clientVersion);
    }

}