package ru.smartel.strike.service.conflict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.conflict.ConflictListDTO;
import ru.smartel.strike.dto.response.conflict.ConflictReportDTO;
import ru.smartel.strike.dto.response.reference.locality.ExtendedLocalityDTO;
import ru.smartel.strike.dto.service.sort.ConflictSortDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.exception.ValidationException;
import ru.smartel.strike.repository.conflict.ConflictReasonRepository;
import ru.smartel.strike.repository.conflict.ConflictRepository;
import ru.smartel.strike.repository.conflict.ConflictResultRepository;
import ru.smartel.strike.repository.etc.IndustryRepository;
import ru.smartel.strike.repository.event.EventRepository;
import ru.smartel.strike.repository.event.EventTypeRepository;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.service.event.EventService;
import ru.smartel.strike.service.filters.FiltersTransformer;
import ru.smartel.strike.specification.conflict.LocalizedConflict;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConflictService {
    public static final Logger log = LoggerFactory.getLogger(ConflictService.class);

    private final ConflictDTOValidator dtoValidator;
    private final FiltersTransformer filtersTransformer;
    private final ConflictRepository conflictRepository;
    private final ConflictReasonRepository conflictReasonRepository;
    private final ConflictResultRepository conflictResultRepository;
    private final IndustryRepository industryRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;
    private final EventTypeRepository eventTypeRepository;

    public ConflictService(ConflictDTOValidator dtoValidator,
                           FiltersTransformer filtersTransformer,
                           ConflictRepository conflictRepository,
                           ConflictReasonRepository conflictReasonRepository,
                           ConflictResultRepository conflictResultRepository,
                           IndustryRepository industryRepository,
                           EventRepository eventRepository,
                           EventService eventService,
                           EventTypeRepository eventTypeRepository) {
        this.dtoValidator = dtoValidator;
        this.filtersTransformer = filtersTransformer;
        this.conflictRepository = conflictRepository;
        this.conflictReasonRepository = conflictReasonRepository;
        this.conflictResultRepository = conflictResultRepository;
        this.industryRepository = industryRepository;
        this.eventRepository = eventRepository;
        this.eventService = eventService;
        this.eventTypeRepository = eventTypeRepository;
    }

    @PreAuthorize("permitAll()")
    public ListWrapperDTO<ConflictListDTO> list(ConflictListRequestDTO dto) {
        dtoValidator.validateListQueryDTO(dto);

        //Transform filters and other restrictions to Specifications
        Specification<Conflict> specification = filtersTransformer
                .toSpecification(dto.getFilters(), null != dto.getUser() ? dto.getUser().getId() : null)
                .and(new LocalizedConflict(dto.getLocale()));

        //Get count of conflicts matching specification
        long conflictsCount = conflictRepository.count(specification);
        ListWrapperDTO.Meta responseMeta = new ListWrapperDTO.Meta(
                conflictsCount,
                dto.getPage(),
                dto.getPerPage());

        if (conflictsCount <= (dto.getPage() - 1) * dto.getPerPage()) {
            return new ListWrapperDTO<>(Collections.emptyList(), responseMeta);
        }

        ConflictSortDTO sortDTO = ConflictSortDTO.of(dto.getSort());

        //Get count of conflicts matching specification. Because pagination and fetching dont work together
        List<Long> ids = conflictRepository.findIds(specification, sortDTO, dto.getPage(), dto.getPerPage());

        List<ConflictListDTO> conflictListDTOS = conflictRepository.findAllById(ids)
                .stream()
                .sorted(sortDTO.toComparator())
                .map(conflict -> ConflictListDTO.of(conflict, dto.getLocale(), dto.isBrief()))
                .collect(toList());

        return new ListWrapperDTO<>(conflictListDTOS, responseMeta);
    }

    @PreAuthorize("permitAll()")
    public ConflictDetailDTO get(long conflictId, Locale locale) {
        return conflictRepository.findById(conflictId)
                .map(c -> ConflictDetailDTO.of(c, locale))
                .orElseThrow(() -> new EntityNotFoundException("Конфликт не найден"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ConflictReportDTO getReportByPeriod(LocalDate from, LocalDate to, List<Long> countriesIds) {
        log.debug("Building report for countries {} from {} to {}", countriesIds, from, to);
        var result = new ConflictReportDTO();
        result.setConflictsBeganBeforeDateFromCount(conflictRepository.getOldConflictsCount(from, to, countriesIds));
        result.setCountByCountries(conflictRepository.getCountByCountries(from, to, countriesIds));
        result.setCountByDistricts(conflictRepository.getCountByDistricts(from, to, countriesIds));
        result.setSpecificCountByDistricts(conflictRepository.getSpecificCountByDistricts(from, to, countriesIds));

        int totalConflictsCount = result.getCountByCountries().values().stream().reduce(0, Integer::sum);

        result.setCountByIndustries(conflictRepository.getCountByIndustries(from, to, countriesIds));
        result.getCountByIndustries().forEach((k, v) -> result.getCountPercentByIndustries().put(k, v * 100 / totalConflictsCount));

        result.setCountByReasons(conflictRepository.getCountByReasons(from, to, countriesIds));
        result.getCountByReasons().forEach((k, v) -> result.getCountPercentByReasons().put(k, v * 100 / totalConflictsCount));

        result.setCountByResults(conflictRepository.getCountByResults(from, to, countriesIds));
        result.getCountByResults().forEach((k, v) -> result.getCountPercentByResults().put(k, v * 100 / totalConflictsCount));

        result.setCountByTypes(conflictRepository.getCountByTypes(from, to, countriesIds));
        result.getCountByTypes().forEach((k, v) -> result.getCountPercentByTypes().put(k, v * 100 / totalConflictsCount));

        result.setCountPercentByResultsByTypes(conflictRepository.getCountPercentByResultsByTypes(from, to, countriesIds));
        result.setCountPercentByResultsByIndustries(conflictRepository.getCountPercentByResultsByIndustries(from, to, countriesIds));
        result.setCountPercentByTypesByIndustries(conflictRepository.getCountPercentByTypesByIndustries(from, to, countriesIds));
        return result;
    }

    public ExtendedLocalityDTO getLatestLocality(long conflictId, Locale locale) {
        //We need to check if conflict exist
        conflictRepository.findById(conflictId)
                .orElseThrow(() -> new EntityNotFoundException("Конфликт не найден"));

        return eventRepository.findFirstByConflictIdAndLocalityNotNullOrderByPostDateDesc(conflictId)
                .map(event -> ExtendedLocalityDTO.of(event.getLocality(), locale))
                .orElseThrow(() -> new EntityNotFoundException("У событий запрошенного конфликта не найдено населенных пунктов"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ConflictDetailDTO create(ConflictCreateRequestDTO dto) {
        log.debug("Creating conflict with dto {}", dto);
        dtoValidator.validateStoreDTO(dto);

        Conflict conflict = new Conflict();
        fillConflictFields(conflict, dto, dto.getLocale());

        Conflict parentConflict = Optional.ofNullable(conflict.getParentEvent())
                .map(Event::getConflict)
                .orElse(null);

        conflictRepository.insertAsLastChildOf(conflict, parentConflict);

        return ConflictDetailDTO.of(conflict, dto.getLocale());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ConflictDetailDTO update(ConflictUpdateRequestDTO dto) {
        log.debug("Updating conflict with dto {}", dto);
        dtoValidator.validateUpdateDTO(dto);

        if (dto.getDateTo() != null && dto.getDateTo().isPresent()) {
            Optional<Event> latestEvent = eventRepository.findFirstByConflictIdOrderByPostDateDesc(dto.getConflictId());

            if (latestEvent.isPresent()) {
                if (latestEvent.get().getDate().toEpochSecond(ZoneOffset.UTC) > dto.getDateTo().get()) {
                    throw new ValidationException(Collections.singletonMap(
                            "dateTo", Collections.singletonList("конфликт не должен кончаться раньше последнего события")));
                }
            }
        }

        if (dto.getDateFrom() != null && dto.getDateFrom().isPresent()) {
            Optional<Event> latestEvent = eventRepository.findFirstByConflictIdOrderByPostDateDesc(dto.getConflictId());

            if (latestEvent.isPresent()) {
                if (latestEvent.get().getDate().toEpochSecond(ZoneOffset.UTC) < dto.getDateFrom().get()) {
                    throw new ValidationException(Collections.singletonMap(
                            "dateFrom", Collections.singletonList("конфликт не должен начинаться позже первого события"))
                    );
                }
            }
        }

        Conflict conflict = conflictRepository.findById(dto.getConflictId())
                .orElseThrow(() -> new EntityNotFoundException("Конфликт не найден"));

        LocalDateTime dateToBeforeUpdate = conflict.getDateTo();

        fillConflictFields(conflict, dto, dto.getLocale());

        Conflict parentConflict = Optional.ofNullable(conflict.getParentEvent())
                .map(Event::getConflict)
                .orElse(null);

        conflictRepository.insertAsLastChildOf(conflict, parentConflict);

        // If dateTo going to be changed - update events' statuses
        if (!Objects.equals(dateToBeforeUpdate, conflict.getDateTo())) {
            eventService.updateConflictsEventStatuses(conflict.getId());
        }

        return ConflictDetailDTO.of(conflict, dto.getLocale());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void delete(long conflictId) {
        log.debug("Removing conflict {}", conflictId);
        Conflict conflict = conflictRepository.findById(conflictId)
                .orElseThrow(() -> new EntityNotFoundException("Конфликт не найден"));

        if (conflictRepository.hasChildren(conflict)) {
            throw new IllegalStateException("В текущей реализации нельзя удалять конфликты, у которых есть потомки");
        }

        conflictRepository.deleteFromTree(conflict);
    }

    private void fillConflictFields(Conflict conflict, ConflictCreateRequestDTO dto, Locale locale) {
        //for the sake of PATCH ;)
        if (null != dto.getTitle()) {
            conflict.setTitleByLocale(locale, dto.getTitle().orElse(null));
        }
        if (null != dto.getTitleRu()) {
            conflict.setTitleRu(dto.getTitleRu().orElse(null));
        }
        if (null != dto.getTitleEn()) {
            conflict.setTitleEn(dto.getTitleEn().orElse(null));
        }
        if (null != dto.getTitleEs()) {
            conflict.setTitleEs(dto.getTitleEs().orElse(null));
        }
        if (null != dto.getTitleDe()) {
            conflict.setTitleDe(dto.getTitleDe().orElse(null));
        }
        if (null != dto.getLatitude()) {
            conflict.setLatitude(dto.getLatitude());
        }
        if (null != dto.getLongitude()) {
            conflict.setLongitude(dto.getLongitude());
        }
        if (null != dto.getCompanyName()) {
            conflict.setCompanyName(dto.getCompanyName().orElse(null));
        }
        if (null != dto.getDateFrom()) {
            conflict.setDateFrom(
                    dto.getDateFrom()
                            .map(date -> LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC))
                            .orElse(null));
        }
        if (null != dto.getDateTo()) {
            conflict.setDateTo(
                    dto.getDateTo()
                            .map(date -> LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC))
                            .orElse(null));
        }

        if (null != dto.getConflictReasonId()) {
            conflict.setReason(dto.getConflictReasonId()
                    .map(conflictReasonRepository::getOne)
                    .orElse(null));
        }
        if (null != dto.getConflictResultId()) {
            conflict.setResult(dto.getConflictResultId()
                    .map(conflictResultRepository::getOne)
                    .orElse(null));
        }
        if (null != dto.getIndustryId()) {
            conflict.setIndustry(dto.getIndustryId()
                    .map(industryRepository::getOne)
                    .orElse(null));
        }
        if (null != dto.getParentEventId()) {
            conflict.setParentEvent(dto.getParentEventId()
                    .map(eventRepository::getOne)
                    .orElse(null));
        }
        if (null != dto.getMainTypeId()) {
            conflict.setMainType(dto.getMainTypeId()
                    .map(eventTypeRepository::getOne)
                    .orElse(null));
            // if once set main type manually, automanaging is disabling
            conflict.setAutomanagingMainType(false);
        }
        if (null != dto.getAutomanagingMainType()) {
            conflict.setAutomanagingMainType(dto.getAutomanagingMainType());
        }
    }
}