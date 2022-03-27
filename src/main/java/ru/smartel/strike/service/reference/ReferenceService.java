package ru.smartel.strike.service.reference;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.response.reference.ReferenceCodeDTO;
import ru.smartel.strike.dto.response.reference.ReferenceNamesDTO;
import ru.smartel.strike.dto.response.reference.ReferenceNamesSlugDTO;
import ru.smartel.strike.dto.service.network.Network;
import ru.smartel.strike.entity.reference.EntityWithNames;
import ru.smartel.strike.entity.reference.EntityWithNamesAndSlug;
import ru.smartel.strike.entity.reference.ReferenceWithCode;
import ru.smartel.strike.repository.conflict.ConflictReasonRepository;
import ru.smartel.strike.repository.conflict.ConflictResultRepository;
import ru.smartel.strike.repository.etc.CountryRepository;
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
public class ReferenceService {

    private final ConflictReasonRepository conflictReasonRepository;
    private final ConflictResultRepository conflictResultRepository;
    private final EventStatusRepository eventStatusRepository;
    private final EventTypeRepository eventTypeRepository;
    private final IndustryRepository industryRepository;
    private final CountryRepository countryRepository;
    private final VideoTypeRepository videoTypeRepository;

    public ReferenceService(ConflictReasonRepository conflictReasonRepository,
                            ConflictResultRepository conflictResultRepository,
                            EventStatusRepository eventStatusRepository,
                            EventTypeRepository eventTypeRepository,
                            IndustryRepository industryRepository,
                            CountryRepository countryRepository,
                            VideoTypeRepository videoTypeRepository) {
        this.conflictReasonRepository = conflictReasonRepository;
        this.conflictResultRepository = conflictResultRepository;
        this.eventStatusRepository = eventStatusRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.industryRepository = industryRepository;
        this.countryRepository = countryRepository;
        this.videoTypeRepository = videoTypeRepository;
    }

    public Map<String, List<?>> getAllReferences(Locale locale) {
        Map<String, List<?>> result = new HashMap<>();

        //references with names
        result.put("conflictReasons", mapReferencesWithNamesToDTOs(conflictReasonRepository.findAll(), locale));
        result.put("conflictResults", mapReferencesWithNamesToDTOs(conflictResultRepository.findAll(), locale));
        result.put("eventTypes", mapReferencesWithNamesToDTOs(eventTypeRepository.findAll(), locale));
        result.put("industries", mapReferencesWithNamesToDTOs(industryRepository.findAll(), locale));
        result.put("countries", mapReferencesWithNamesToDTOs(countryRepository.findAll(), locale));
        //references with names and slug
        result.put("eventStatuses", mapReferencesWithNamesAndSlugToDTOs(eventStatusRepository.findAll(), locale));
        //references with code
        result.put("videoTypes", mapReferencesWithCodeToDTOs(videoTypeRepository.findAll()));
        result.put("networks", Stream.of(Network.values())
                .map(n -> ReferenceCodeDTO.of(n.getId(), n.getSlug()))
                .collect(toList()));

        return result;
    }

    public String getChecksum() {
        //stream of reference JPA repositories
        Stream<JpaRepository<?, ?>> repositories = Stream.of(
                conflictReasonRepository,
                conflictResultRepository,
                eventStatusRepository,
                eventTypeRepository,
                industryRepository,
                countryRepository,
                videoTypeRepository
        );

        //Result hash should not depend on database order
        Sort sortByIdAsc = Sort.by(Sort.Direction.ASC, "id");

        //checksum is superposition of hashCodes of reference lists
        int referencesHash =
                repositories.map(repo -> repo.findAll(sortByIdAsc).hashCode())
                        .collect(Collectors.toList())
                        .hashCode();

        return String.valueOf(referencesHash);
    }

    private List<ReferenceNamesDTO> mapReferencesWithNamesToDTOs(List<? extends EntityWithNames> references, Locale locale) {
        return references.stream()
                .map(ref -> ReferenceNamesDTO.of(ref, locale))
                .collect(toList());
    }

    private List<ReferenceNamesSlugDTO> mapReferencesWithNamesAndSlugToDTOs(List<? extends EntityWithNamesAndSlug> references, Locale locale) {
        return references.stream()
                .map(ref -> ReferenceNamesSlugDTO.of(ref, locale))
                .collect(toList());
    }

    private List<ReferenceCodeDTO> mapReferencesWithCodeToDTOs(List<? extends ReferenceWithCode> references) {
        return references.stream()
                .map(ReferenceCodeDTO::from)
                .collect(toList());
    }
}
