package ru.smartel.strike.service.locality;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.locality.LocalityDetailDTO;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.repository.etc.LocalityRepository;
import ru.smartel.strike.repository.etc.RegionRepository;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.specification.locality.LocalityOfRegion;
import ru.smartel.strike.specification.locality.NamePatternLocality;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class LocalityService {

    private LocalityRepository localityRepository;
    private RegionRepository regionRepository;
    private LocalityDTOValidator validator;

    public LocalityService(LocalityRepository localityRepository,
                           RegionRepository regionRepository,
                           LocalityDTOValidator validator) {
        this.localityRepository = localityRepository;
        this.regionRepository = regionRepository;
        this.validator = validator;
    }

    public ListWrapperDTO<LocalityDetailDTO> list(String name, Integer regionId, Locale locale) {
        List<Locality> localities = localityRepository.findAll(
                new LocalityOfRegion(regionId).and(
                        //name can be null
                        Optional.ofNullable(name).map(NamePatternLocality::new).orElse(null))
        );

        return new ListWrapperDTO<>(
                localities.stream()
                        .map(locality -> LocalityDetailDTO.of(locality, locale))
                        .sorted(Comparator.comparing(LocalityDetailDTO::getName))
                        .collect(Collectors.toList()),
                null
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public LocalityDetailDTO create(LocalityCreateRequestDTO dto) {
        validator.validateCreateDTO(dto);

        Locality locality = new Locality();
        locality.setName(dto.getName());
        locality.setRegion(regionRepository.getOne(dto.getRegionId()));
        localityRepository.save(locality);

        return LocalityDetailDTO.of(locality, dto.getLocale());
    }
}
