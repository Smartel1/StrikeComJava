package ru.smartel.strike.service.client_version;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

    private final long ID = 0;
    private final String VERSION = "version";
    private final String DESCRIPTION_DE = "descriptionDe";
    private final String DESCRIPTION_RU = "descriptionRu";
    private final String DESCRIPTION_EN = "descriptionEn";
    private final String DESCRIPTION_ES = "descriptionEs";
    private final boolean isRequired = false;

    private ClientVersionCreateRequestDTO getCreateRequestDto() {
        return new ClientVersionCreateRequestDTO.Builder()
                .clientId("id")
                .required(isRequired)
                .locale(Locale.RU)
                .version(VERSION)
                .descriptionDe(DESCRIPTION_DE)
                .descriptionEn(DESCRIPTION_EN)
                .descriptionEs(DESCRIPTION_ES)
                .descriptionRu(DESCRIPTION_RU)
                .build();
    }

    private ClientVersionDTO getClientVersionDTO() {
        return new ClientVersionDTO.Builder()
                .id(ID)
                .isRequired(isRequired)
                .version(VERSION)
                .build();
    }

    // Проверка на выкидывание ValidationException
    @Test
    void whenGetNewVersionsShouldThrowValidationException() {
        var getNewRequestDto = new ClientVersionGetNewRequestDTO.Builder()
                .clientId(VERSION)
                .currentVersion("CURRENT_" + VERSION)
                .locale(Locale.RU)
                .build();

        when(repository.getByVersionAndClientId(getNewRequestDto.getCurrentVersion(), getNewRequestDto.getClientId())).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> service.getNewVersions(getNewRequestDto));

        String actual = exception.getMessage() + exception.getErrors().toString();

        String expected = "Validation error occurred" + Collections.singletonMap("error", Collections.singletonList(
                "Нет такой версии: " + getNewRequestDto.getClientId() + ":" + getNewRequestDto.getCurrentVersion())).toString();

        assertTrue(actual.contains(expected));
    }

    @Test
    void whenGetNewVersionsShouldReturnListWrapperDto() {
        var getNewRequestDto = new ClientVersionGetNewRequestDTO.Builder()
                .clientId(VERSION)
                .locale(Locale.RU)
                .build();

        var clientVersion = new ClientVersion();
        clientVersion.setVersion(VERSION);

        when(repository.findAllByClientId(any(String.class))).thenReturn(Arrays.asList(clientVersion));
        ListWrapperDTO<ClientVersionDTO> versions = service.getNewVersions(getNewRequestDto);

        var actual = versions.getData().get(0);
        var expected = new ClientVersionDTO.Builder()
                .version(getNewRequestDto.getClientId())
                .build();

        assertThat(actual).isEqualTo(expected);
    }

    // Проверка кейса с Locale.RU
    @ParameterizedTest
    @EnumSource(value = Locale.class, names = {"ALL", "RU"})
    void whenCreateAndSameVersionIsNotPresetItShouldReturnClientVersionDTO(Locale locale) {
        var requestDto = getCreateRequestDto();
        requestDto.setLocale(locale);

        when(repository.getByVersionAndClientId(requestDto.getVersion(), requestDto.getClientId())).thenReturn(Optional.empty());

        var actual = service.create(requestDto);
        //todo: здесь не понял, как надо. Понятно, что мы хотим проверить, что repository.save() вызывалось,
        // но какой объект надо передать в save я не смог выявить, если передать что-то конкретное, то идет конфликт
        // Actual invocations have different arguments: repository.getByVersionAndClientId
        // Или всё-таки достаточно оставить как есть, т.е Mockito.any() ?
        verify(repository).save(Mockito.any());

        var expected = getClientVersionDTO();
        expected.add("description", requestDto.getDescriptionRu());

        // Переопределил hashCode и equals в ClientVersionDTO, иначе не работало
        assertThat(actual).isEqualTo(expected);
    }

    // Проверка на выкидывание ValidationException
    @Test
    void whenCreateAndSameVersionIsPresetItShouldThrowValidationException() {
        var requestDto = getCreateRequestDto();
        when(repository.getByVersionAndClientId(requestDto.getVersion(), requestDto.getClientId()))
                .thenReturn(Optional.of(new ClientVersion()));

        ValidationException exception = assertThrows(ValidationException.class, () -> service.create(requestDto));

        String actual = exception.getMessage() + exception.getErrors().toString();
        String expected = Collections.singletonMap("error", Collections.singletonList("Такая версия уже существует")).toString();

        assertTrue(actual.contains(expected));
    }

    // Проверка на выкидывание EntityNotFoundException
    @Test
    void whenDeleteIsAbsentClientShouldThrowEntityNotFoundException() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.delete(ID));

        String actual = exception.getMessage();
        String expected = "Версия клиента не найдена";

        assertTrue(actual.contains(expected));
    }

    @Test
    void whenDeleteShouldTrue() {
        var clientVersion = Optional.of(new ClientVersion());

        when(repository.findById(ID)).thenReturn(clientVersion);

        service.delete(ID);
        verify(repository).delete(clientVersion.get());
    }

}