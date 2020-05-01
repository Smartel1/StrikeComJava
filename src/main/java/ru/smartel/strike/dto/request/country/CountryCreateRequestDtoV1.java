package ru.smartel.strike.dto.request.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.smartel.strike.service.Locale;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CountryCreateRequestDtoV1 {
    @JsonIgnore
    @NotNull
    private Locale locale;

    @NotNull
    @Size(min = 1)
    private String nameRu;

    @NotNull
    @Size(min = 1)
    private String nameEn;

    @NotNull
    @Size(min = 1)
    private String nameEs;

    @NotNull
    @Size(min = 1)
    private String nameDe;
}
