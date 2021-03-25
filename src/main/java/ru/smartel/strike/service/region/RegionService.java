package ru.smartel.strike.service.region;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.region.RegionCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.locality.LocalityDetailDTO;
import ru.smartel.strike.dto.response.reference.region.RegionDetailDTO;
import ru.smartel.strike.entity.reference.Region;
import ru.smartel.strike.repository.etc.CountryRepository;
import ru.smartel.strike.repository.etc.RegionRepository;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.specification.region.NamePatternRegion;
import ru.smartel.strike.specification.region.RegionOfCountry;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegionService {

    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final RegionDTOValidator validator;

    public RegionService(RegionRepository regionRepository,
                         CountryRepository countryRepository,
                         RegionDTOValidator validator) {
        this.regionRepository = regionRepository;
        this.countryRepository = countryRepository;
        this.validator = validator;
    }

    public ListWrapperDTO<RegionDetailDTO> list(String name, Integer countryId, Locale locale) {
        List<Region> regions = regionRepository.findAll(
                new RegionOfCountry(countryId).and(
                        //name can be null
                        Optional.ofNullable(name).map(NamePatternRegion::new).orElse(null))
        );

        return new ListWrapperDTO<>(
                regions.stream()
                        .map(region -> RegionDetailDTO.of(region, locale))
                        .sorted(Comparator.comparing(RegionDetailDTO::getName))
                        .collect(Collectors.toList()),
                null
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public RegionDetailDTO create(RegionCreateRequestDTO dto) {
        validator.validateCreateDTO(dto);

        Region region = new Region();
        region.setName(dto.getName());
        region.setCountry(countryRepository.getOne(dto.getCountryId()));
        regionRepository.save(region);

        return RegionDetailDTO.of(region, dto.getLocale());
    }
}
