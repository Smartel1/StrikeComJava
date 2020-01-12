package ru.smartel.strike.service.conflict;

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
import ru.smartel.strike.dto.response.reference.locality.ExtendedLocalityDTO;
import ru.smartel.strike.dto.service.sort.ConflictSortDTO;
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
import ru.smartel.strike.service.filters.FiltersTransformer;
import ru.smartel.strike.specification.conflict.LocalizedConflict;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConflictServiceImpl implements ConflictService {

    private ConflictDTOValidator dtoValidator;
    private FiltersTransformer filtersTransformer;
    private ConflictRepository conflictRepository;
    private ConflictReasonRepository conflictReasonRepository;
    private ConflictResultRepository conflictResultRepository;
    private IndustryRepository industryRepository;
    private EventRepository eventRepository;

    public ConflictServiceImpl(ConflictDTOValidator dtoValidator,
                               FiltersTransformer filtersTransformer,
                               ConflictRepository conflictRepository,
                               ConflictReasonRepository conflictReasonRepository,
                               ConflictResultRepository conflictResultRepository,
                               IndustryRepository industryRepository,
                               EventRepository eventRepository) {
        this.dtoValidator = dtoValidator;
        this.filtersTransformer = filtersTransformer;
        this.conflictRepository = conflictRepository;
        this.conflictReasonRepository = conflictReasonRepository;
        this.conflictResultRepository = conflictResultRepository;
        this.industryRepository = industryRepository;
        this.eventRepository = eventRepository;
    }

    @Override
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
                dto.getPerPage()
        );

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
                .collect(Collectors.toList());

        return new ListWrapperDTO<>(conflictListDTOS, responseMeta);
    }

    @Override
    @PreAuthorize("permitAll()")
    public ConflictDetailDTO get(long conflictId, Locale locale) {
        Conflict conflict = conflictRepository.findById(conflictId)
                .orElseThrow(() -> new EntityNotFoundException("Конфликт не найден"));

        return ConflictDetailDTO.of(conflict, locale);
    }

    @Override
    public ExtendedLocalityDTO getLatestCoordinates(long conflictId, Locale locale) {
        //We need to check if conflict exist
        Conflict conflict = conflictRepository.findById(conflictId)
                .orElseThrow(() -> new EntityNotFoundException("Конфликт не найден"));

        return eventRepository.findFirstByConflictIdAndLocalityNotNullOrderByPostDateDesc(conflictId)
                .map(event -> ExtendedLocalityDTO.of(event.getLocality(), locale))
                .orElseThrow(() -> new EntityNotFoundException("У событий запрошенного конфликта не найдено населенных пунктов"));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ConflictDetailDTO create(ConflictCreateRequestDTO dto) {
        dtoValidator.validateStoreDTO(dto);

        Conflict conflict = new Conflict();
        fillConflictFields(conflict, dto, dto.getLocale());

        Conflict parentConflict = Optional.ofNullable(conflict.getParentEvent())
                .map(Event::getConflict)
                .orElse(null);

        conflictRepository.insertAsLastChildOf(conflict, parentConflict);

        return ConflictDetailDTO.of(conflict, dto.getLocale());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ConflictDetailDTO update(ConflictUpdateRequestDTO dto) {
        dtoValidator.validateUpdateDTO(dto);

        Conflict conflict = conflictRepository.findById(dto.getConflictId())
                .orElseThrow(() -> new EntityNotFoundException("Конфликт не найден"));

        fillConflictFields(conflict, dto, dto.getLocale());

        Conflict parentConflict = Optional.ofNullable(conflict.getParentEvent())
                .map(Event::getConflict)
                .orElse(null);

        conflictRepository.insertAsLastChildOf(conflict, parentConflict);

        return ConflictDetailDTO.of(conflict, dto.getLocale());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void delete(long conflictId) {
        Conflict conflict = conflictRepository.findById(conflictId)
                .orElseThrow(() -> new EntityNotFoundException("Конфликт не найден"));

        if (conflictRepository.hasChildren(conflict)) {
            throw new IllegalStateException("В текущей реализации нельзя удалять конфликты, у которых есть потомки");
        };

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
            dto.getDateFrom()
                    .map(date -> LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC))
                    .ifPresent(conflict::setDateFrom);
        }
        if (null != dto.getDateTo()){
            dto.getDateTo()
                    .map(date -> LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC))
                    .ifPresent(conflict::setDateTo);
        }

        if (null != dto.getConflictReasonId()) {
            setReason(conflict, dto.getConflictReasonId().orElse(null));
        }
        if (null != dto.getConflictResultId()) {
            setResult(conflict, dto.getConflictResultId().orElse(null));
        }
        if (null != dto.getIndustryId()) {
            setIndustry(conflict, dto.getIndustryId().orElse(null));
        }
        if (null != dto.getParentEventId()) {
            setParentEvent(conflict, dto.getParentEventId().orElse(null));
        }
    }

    private void setReason(Conflict conflict, Long reasonId){
        ConflictReason conflictReason = null;

        if (null != reasonId) {
            conflictReason = conflictReasonRepository.getOne(reasonId);
        }

        conflict.setReason(conflictReason);
    }

    private void setResult(Conflict conflict, Long resultId){
        ConflictResult conflictResult = null;

        if (null != resultId) {
            conflictResult = conflictResultRepository.getOne(resultId);
        }

        conflict.setResult(conflictResult);
    }

    private void setIndustry(Conflict conflict, Long industryId){
        Industry industry = null;

        if (null != industryId) {
            industry = industryRepository.getOne(industryId);
        }

        conflict.setIndustry(industry);
    }

    private void setParentEvent(Conflict conflict, Long parentEventId){
        Event parentEvent = null;

        if (null != parentEventId) {
            parentEvent = eventRepository.getOne(parentEventId);
        }

        conflict.setParentEvent(parentEvent);
    }
}
