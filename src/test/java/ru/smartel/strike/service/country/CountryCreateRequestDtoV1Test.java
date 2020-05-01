package ru.smartel.strike.service.country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.smartel.strike.dto.request.country.CountryCreateRequestDtoV1;
import ru.smartel.strike.service.Locale;

import javax.validation.Validation;
import javax.validation.Validator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CountryCreateRequestDtoV1Test {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldHaveNoViolations() {
        //given:
        var requestDto = new CountryCreateRequestDtoV1();
        requestDto.setLocale(Locale.RU);
        requestDto.setNameRu("Ро");
        requestDto.setNameDe("RU");
        requestDto.setNameEn("RU");
        requestDto.setNameEs("RU");

        //when:
        var violations = validator.validate(requestDto);

        //then:
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldDetectInvalidLocale() {
        //given:
        var requestDto = new CountryCreateRequestDtoV1();
        requestDto.setNameRu("RU");
        requestDto.setNameDe("RU");
        requestDto.setNameEn("RU");
        requestDto.setNameEs("RU");

        //when:
        var violations = validator.validate(requestDto);

        //then:
        assertEquals(1, violations.size());

        var violation = violations.iterator().next();
        //todo: разобраться с локалью сообщений (убрать todo после ревью или решения)
        assertEquals("должно быть задано", violation.getMessage());
        assertEquals("locale", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    void shouldDetectInvalidNames() {
        //given:
        var requestDto = new CountryCreateRequestDtoV1();
        requestDto.setLocale(Locale.RU);

        //when:
        var violations = validator.validate(requestDto);

        //then:
        assertEquals(4, violations.size());

        var violation = violations.iterator().next();

        assertEquals("должно быть задано", violation.getMessage());
        //todo: возможно стоит использовать рефлексию
        assertTrue(Stream.of("nameRu", "nameDe", "nameEs", "nameEn")
                .anyMatch(str -> str.trim().equals(violation.getPropertyPath().toString()))
        );
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    void shouldDetectInvalidShortNames() {
        //given:
        var requestDto = new CountryCreateRequestDtoV1();
        requestDto.setLocale(Locale.RU);
        requestDto.setNameRu("");
        requestDto.setNameDe("");
        requestDto.setNameEn("");
        requestDto.setNameEs("");

        //when:
        var violations = validator.validate(requestDto);

        //then:
        assertEquals(4, violations.size());

        var violation = violations.iterator().next();
        assertEquals("размер должен быть между 1 и 2147483647", violation.getMessage());
        assertEquals("", violation.getInvalidValue());
    }
}