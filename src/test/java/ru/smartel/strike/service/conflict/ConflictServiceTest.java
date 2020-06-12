package ru.smartel.strike.service.conflict;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.reference.ConflictReason;
import ru.smartel.strike.entity.reference.ConflictResult;
import ru.smartel.strike.entity.reference.Industry;
import ru.smartel.strike.repository.conflict.ConflictReasonRepository;
import ru.smartel.strike.repository.conflict.ConflictRepository;
import ru.smartel.strike.repository.conflict.ConflictResultRepository;
import ru.smartel.strike.repository.etc.IndustryRepository;
import ru.smartel.strike.repository.event.EventRepository;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.event.EventService;
import ru.smartel.strike.service.filters.FiltersTransformer;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConflictServiceTest {

    @InjectMocks
    private ConflictService service;

    @Mock
    private ConflictDTOValidator dtoValidator;
    @Mock
    private FiltersTransformer filtersTransformer;
    @Mock
    private ConflictRepository conflictRepository;
    @Mock
    private ConflictReasonRepository conflictReasonRepository;
    @Mock
    private ConflictResultRepository conflictResultRepository;
    @Mock
    private IndustryRepository industryRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventService eventService;
    private long id = 1;

    @Test
    void delete_whenConflictNotExist_thenThrow() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.delete(id));
        assertThat(exception.getMessage()).isEqualTo("Конфликт не найден");
    }

    @Test
    void delete_whenConflictExist_butHasChildren_thenThrow() {
        // given
        Conflict conflict = new Conflict();
        when(conflictRepository.findById(id)).thenReturn(Optional.of(conflict));
        when(conflictRepository.hasChildren(conflict)).thenReturn(true);
        // when - then
        assertThrows(IllegalStateException.class, () -> service.delete(id));
    }

    @Test
    void delete_whenConflictExist_andDoesntHaveChildren_thenDelete() {
        // given
        Conflict conflict = new Conflict();
        when(conflictRepository.findById(id)).thenReturn(Optional.of(conflict));
        when(conflictRepository.hasChildren(conflict)).thenReturn(false);
        // when
        service.delete(id);
        // then
        verify(conflictRepository).deleteFromTree(conflict);
    }

    @Test
    void get_whenConflictDoesntExist_thenThrow() {
        assertThrows(EntityNotFoundException.class, () -> service.get(id, Locale.RU));
    }

    @Test
    void get_whenConflictExist_localeRu() {
        // given
        Conflict conflict = conflict();
        ConflictDetailDTO expected = conflictDetailDTO(conflict, Locale.RU);
        when(conflictRepository.findById(id)).thenReturn(Optional.of(conflict));
        // when
        ConflictDetailDTO actual = service.get(id, Locale.RU);
        // then
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @Test
    void get_whenConflictExist_localeAll() {
        // given
        Conflict conflict = conflict();
        ConflictDetailDTO expected = conflictDetailDTO(conflict, Locale.ALL);
        when(conflictRepository.findById(id)).thenReturn(Optional.of(conflict));
        // when
        ConflictDetailDTO actual = service.get(id, Locale.ALL);
        // then
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    private ConflictDetailDTO conflictDetailDTO(Conflict conflict, Locale locale) {
        return ConflictDetailDTO.of(conflict, locale);
    }

    private Conflict conflict() {
        Conflict conflict = new Conflict();
        conflict.setTitleRu("title ru");
        conflict.setLatitude(1010F);
        conflict.setLongitude(2020F);
        conflict.setCompanyName("CompanyName");

        LocalDateTime dateFrom = LocalDateTime.of(2020, 1, 5, 1, 0);
        conflict.setDateFrom(dateFrom);

        LocalDateTime dateTo = LocalDateTime.of(2022, 1, 5, 1, 0);
        conflict.setDateTo(dateTo);

        ConflictReason reason = new ConflictReason();
        reason.setId(88L);
        conflict.setReason(reason);

        ConflictResult result = new ConflictResult();
        result.setId(22L);
        conflict.setResult(result);

        Industry industry = new Industry();
        industry.setId(11L);
        conflict.setIndustry(industry);

        Event parentEvent = new Event();
        parentEvent.setId(44L);
        conflict.setParentEvent(parentEvent);
        return conflict;
    }
}



