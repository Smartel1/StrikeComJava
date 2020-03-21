package ru.smartel.strike.service.country;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.country.CountryDTO;
import ru.smartel.strike.entity.reference.Country;
import ru.smartel.strike.repository.etc.CountryRepository;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.specification.country.NamePatternCountry;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CountryService {

    private CountryRepository countryRepository;
    private CountryDTOValidator validator;

    public CountryService(CountryRepository countryRepository,
                          CountryDTOValidator validator) {
        this.countryRepository = countryRepository;
        this.validator = validator;
    }

    public ListWrapperDTO<CountryDTO> list(String name, Locale locale) {
        //find country using name (if name specified)
        List<Country> countries = countryRepository.findAll(
                Optional.ofNullable(name).map(NamePatternCountry::new).orElse(null));

        return new ListWrapperDTO<>(
                countries.stream()
                        .map(country -> CountryDTO.of(country, locale))
                        .collect(Collectors.toList()),
                null
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public CountryDTO create(CountryCreateRequestDTO dto) {
        validator.validateCreateDTO(dto);

        Country country = new Country();
        country.setNameRu(dto.getNameRu());
        country.setNameEn(dto.getNameEn());
        country.setNameEs(dto.getNameEs());
        country.setNameDe(dto.getNameDe());
        countryRepository.save(country);

        return CountryDTO.of(country, dto.getLocale());
    }
}
