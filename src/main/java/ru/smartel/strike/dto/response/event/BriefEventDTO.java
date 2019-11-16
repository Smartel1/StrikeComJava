package ru.smartel.strike.dto.response.event;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import ru.smartel.strike.dto.response.ExtendableDTO;
import ru.smartel.strike.entity.interfaces.Titles;
import ru.smartel.strike.service.Locale;

import java.util.Optional;

public class BriefEventDTO {
    private long id;
    private long date;
    @JsonUnwrapped
    private ExtendableDTO titles = new ExtendableDTO();

    public void setTitlesOf(Titles entity, Locale locale) {
        if (locale.equals(Locale.ALL)) {
            titles.add("titleRu", entity.getTitleRu());
            titles.add("titleEn", entity.getTitleEn());
            titles.add("titleEs", entity.getTitleEs());
        } else {
            //If title is not localized use default (Ru)
            String byLocale = entity.getTitleByLocale(locale);
            titles.add("title", byLocale == null || byLocale.isBlank()
                    ? Optional.ofNullable(entity.getTitleRu()).orElse("")
                    : byLocale);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
