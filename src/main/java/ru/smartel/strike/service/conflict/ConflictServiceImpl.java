package ru.smartel.strike.service.conflict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.conflict.ConflictListDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.entity.reference.ConflictReason;
import ru.smartel.strike.entity.reference.ConflictResult;
import ru.smartel.strike.entity.reference.Industry;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.repository.conflict.ConflictReasonRepository;
import ru.smartel.strike.repository.conflict.ConflictRepository;
import ru.smartel.strike.repository.conflict.ConflictResultRepository;
import ru.smartel.strike.repository.event.EventRepository;
import ru.smartel.strike.repository.reference.IndustryRepository;
import ru.smartel.strike.service.filters.FiltersTransformer;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.specification.conflict.LocalizedConflict;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    public ListWrapperDTO<ConflictListDTO> list(ConflictListRequestDTO dto, int perPage, int page, Locale locale, User user, boolean brief) throws DTOValidationException {
        dtoValidator.validateListQueryDTO(dto);

        //Body has precedence over query params.
        //If perPage and Page of body (dto) aren't equal to default values then use values of dto
        if (ConflictListRequestDTO.DEFAULT_PAGE_CAPACITY != dto.getPerPage()) perPage = dto.getPerPage();
        if (ConflictListRequestDTO.DEFAULT_PAGE != dto.getPage()) page = dto.getPage();

        //Transform filters and other restrictions to Specifications
        Specification<Conflict> specification = filtersTransformer
                .toSpecification(dto.getFilters(), null != user ? user.getId() : null)
                .and(new LocalizedConflict(locale));

        //Get count of conflicts matching specification
        long conflictsCount = conflictRepository.count(specification);
        ListWrapperDTO.Meta responseMeta = new ListWrapperDTO.Meta(
                conflictsCount,
                page,
                perPage
        );

        if (conflictsCount <= (page - 1) * perPage) {
            return new ListWrapperDTO<>(Collections.emptyList(), responseMeta);
        }

        //Get count of conflicts matching specification. Because pagination and fetching dont work together
        List<Integer> ids = conflictRepository.findIds(specification, page, perPage);

        List<ConflictListDTO> conflictListDTOS = conflictRepository.findAllById(ids)
                .stream()
                .map(e -> new ConflictListDTO(e, locale, brief))
                .collect(Collectors.toList());

        return new ListWrapperDTO<>(conflictListDTOS, responseMeta);
    }

    @Override
    @PreAuthorize("permitAll()")
    public ConflictDetailDTO get(Integer conflictId, Locale locale) {
        Conflict conflict = conflictRepository.findOrThrow(conflictId);
        return new ConflictDetailDTO(conflict, locale);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ConflictDetailDTO create(ConflictRequestDTO dto, Integer userId, Locale locale) throws BusinessRuleValidationException, DTOValidationException {
        dtoValidator.validateStoreDTO(dto);

        Conflict conflict = new Conflict();
        fillConflictFields(conflict, dto, locale);

        conflictRepository.save(conflict);

        return new ConflictDetailDTO(conflict, locale);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ConflictDetailDTO update(Integer conflictId, ConflictRequestDTO dto, Integer userId, Locale locale) throws BusinessRuleValidationException, DTOValidationException {
        dtoValidator.validateUpdateDTO(dto);
        Conflict conflict = conflictRepository.findOrThrow(conflictId);
        fillConflictFields(conflict, dto, locale);

        conflictRepository.save(conflict);
        return new ConflictDetailDTO(conflict, locale);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void delete(Integer conflictId) throws BusinessRuleValidationException {
        Conflict conflict = conflictRepository.findOrThrow(conflictId);
        conflictRepository.delete(conflict);
    }

    private void fillConflictFields(Conflict conflict, ConflictRequestDTO dto, Locale locale){
        if (null != dto.getTitle()) conflict.setTitleByLocale(locale, dto.getTitle().orElse(null));
        if (null != dto.getTitleRu()) conflict.setTitleRu(dto.getTitleRu().orElse(null));
        if (null != dto.getTitleEn()) conflict.setTitleEn(dto.getTitleEn().orElse(null));
        if (null != dto.getTitleEs()) conflict.setTitleEs(dto.getTitleEs().orElse(null));
        if (null != dto.getLatitude()) conflict.setLatitude(dto.getLatitude());
        if (null != dto.getLongitude()) conflict.setLongitude(dto.getLongitude());
        if (null != dto.getCompanyName()) conflict.setCompanyName(dto.getCompanyName().orElse(null));
        if (null != dto.getDateFrom())
            conflict.setDateFrom(LocalDateTime.ofEpochSecond(dto.getDateFrom().orElseThrow(), 0, ZoneOffset.UTC));
        if (null != dto.getDateTo())
                    conflict.setDateTo(LocalDateTime.ofEpochSecond(dto.getDateTo().orElseThrow(), 0, ZoneOffset.UTC));

        if (null != dto.getConflictReasonId()) setReason(conflict, dto.getConflictReasonId().orElse(null));
        if (null != dto.getConflictResultId()) setResult(conflict, dto.getConflictResultId().orElse(null));
        if (null != dto.getIndustryId()) setIndustry(conflict, dto.getIndustryId().orElse(null));
        if (null != dto.getParentEventId()) setParentEvent(conflict, dto.getParentEventId().orElse(null));
    }

    private void setReason(Conflict conflict, Integer reasonId){
        ConflictReason conflictReason = null;

        if (null != reasonId) {
            conflictReason = conflictReasonRepository.getOne(reasonId);
        }

        conflict.setReason(conflictReason);
    }

    private void setResult(Conflict conflict, Integer resultId){
        ConflictResult conflictResult = null;

        if (null != resultId) {
            conflictResult = conflictResultRepository.getOne(resultId);
        }

        conflict.setResult(conflictResult);
    }

    private void setIndustry(Conflict conflict, Integer industryId){
        Industry industry = null;

        if (null != industryId) {
            industry = industryRepository.getOne(industryId);
        }

        conflict.setIndustry(industry);
    }

    private void setParentEvent(Conflict conflict, Integer parentEventId){
        Event parentEvent = null;

        if (null != parentEventId) {
            parentEvent = eventRepository.getOne(parentEventId);
        }

        conflict.setParentEvent(parentEvent);
    }
}
