package ru.smartel.strike.service.country;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.country.CountryDTO;
import ru.smartel.strike.entity.reference.Country;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.repository.etc.CountryRepository;
import ru.smartel.strike.service.Locale;
import ru.smartel.strike.specification.country.NamePatternCountry;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository;
    private CountryDTOValidator validator;

    public CountryServiceImpl(CountryRepository countryRepository,
                              CountryDTOValidator validator) {
        this.countryRepository = countryRepository;
        this.validator = validator;
    }

    @Override
    public ListWrapperDTO<CountryDTO> list(String name, Locale locale) {
        List<Country> countries = countryRepository.findAll(
                new NamePatternCountry(name)
        );

        return new ListWrapperDTO<>(
                countries.stream()
                        .map(country -> new CountryDTO(country, locale))
                        .collect(Collectors.toList()),
                null
        );
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public CountryDTO create(CountryCreateRequestDTO dto) throws DTOValidationException {
        validator.validateCreateDTO(dto);

        Country country = new Country();
        country.setNameRu(dto.getNameRu());
        country.setNameEn(dto.getNameEn());
        country.setNameEs(dto.getNameEs());
        countryRepository.save(country);

        return new CountryDTO(country, dto.getLocale());
    }
}
