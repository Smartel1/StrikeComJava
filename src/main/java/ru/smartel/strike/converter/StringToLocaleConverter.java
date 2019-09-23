package ru.smartel.strike.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.smartel.strike.service.Locale;

import javax.persistence.EntityNotFoundException;

@Component
public class StringToLocaleConverter implements Converter<String, Locale> {

    @Override
    public Locale convert(String locale) throws EntityNotFoundException {
        return Locale.valueOf(locale.toUpperCase());
    }
}
