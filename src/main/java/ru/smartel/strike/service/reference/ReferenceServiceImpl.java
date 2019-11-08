package ru.smartel.strike.service.reference;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.response.reference.ReferenceCodeDTO;
import ru.smartel.strike.dto.response.reference.ReferenceNamesDTO;
import ru.smartel.strike.entity.interfaces.NamedReference;
import ru.smartel.strike.entity.interfaces.ReferenceWithCode;
import ru.smartel.strike.repository.conflict.ConflictReasonRepository;
import ru.smartel.strike.repository.conflict.ConflictResultRepository;
import ru.smartel.strike.repository.etc.IndustryRepository;
import ru.smartel.strike.repository.etc.VideoTypeRepository;
import ru.smartel.strike.repository.event.EventStatusRepository;
import ru.smartel.strike.repository.event.EventTypeRepository;
import ru.smartel.strike.service.Locale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class ReferenceServiceImpl implements ReferenceService {

    private ConflictReasonRepository conflictReasonRepository;
    private ConflictResultRepository conflictResultRepository;
    private EventStatusRepository eventStatusRepository;
    private EventTypeRepository eventTypeRepository;
    private IndustryRepository industryRepository;
    private VideoTypeRepository videoTypeRepository;

    public ReferenceServiceImpl(ConflictReasonRepository conflictReasonRepository,
                                ConflictResultRepository conflictResultRepository,
                                EventStatusRepository eventStatusRepository,
                                EventTypeRepository eventTypeRepository,
                                IndustryRepository industryRepository,
                                VideoTypeRepository videoTypeRepository) {
        this.conflictReasonRepository = conflictReasonRepository;
        this.conflictResultRepository = conflictResultRepository;
        this.eventStatusRepository = eventStatusRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.industryRepository = industryRepository;
        this.videoTypeRepository = videoTypeRepository;
    }

    @Override
    public Map<String, List<?>> getAllReferences(Locale locale) {
        Map<String, List<?>> result = new HashMap<>();

        //references with names
        result.put("conflictReasons", mapReferencesWithNamesToDTOs(conflictReasonRepository.findAll(), locale));
        result.put("conflictResults", mapReferencesWithNamesToDTOs(conflictResultRepository.findAll(), locale));
        result.put("eventStatuses", mapReferencesWithNamesToDTOs(eventStatusRepository.findAll(), locale));
        result.put("eventTypes", mapReferencesWithNamesToDTOs(eventTypeRepository.findAll(), locale));
        result.put("industries", mapReferencesWithNamesToDTOs(industryRepository.findAll(), locale));

        //references with code
        result.put("videoTypes", mapReferencesWithCodeToDTOs(videoTypeRepository.findAll()));

        return result;
    }

    @Override
    public String getChecksum() {
        //stream of reference JPA repositories
        Stream<JpaRepository> repositories = Stream.of(
                conflictReasonRepository,
                conflictResultRepository,
                eventStatusRepository,
                eventTypeRepository,
                industryRepository,
                videoTypeRepository
        );

        //Result hash should not depend on database order
        Sort sortByIdAsc = new Sort(Sort.Direction.ASC, "id");

        //checksum is superposition of hashCodes of reference lists
        int referencesHash =
                repositories.map(repo -> repo.findAll(sortByIdAsc).hashCode())
                        .collect(Collectors.toList())
                        .hashCode();

        return String.valueOf(referencesHash);
    }

    private List<ReferenceNamesDTO> mapReferencesWithNamesToDTOs(List<? extends NamedReference> references, Locale locale) {
        return references.stream()
                .map(ref -> new ReferenceNamesDTO(ref, locale))
                .collect(toList());
    }

    private List<ReferenceCodeDTO> mapReferencesWithCodeToDTOs(List<? extends ReferenceWithCode> references) {
        return references.stream()
                .map(ReferenceCodeDTO::new)
                .collect(toList());
    }
}
