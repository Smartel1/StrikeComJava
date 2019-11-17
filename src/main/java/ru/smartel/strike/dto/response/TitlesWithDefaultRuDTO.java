package ru.smartel.strike.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.smartel.strike.entity.interfaces.Titles;
import ru.smartel.strike.service.Locale;

import java.util.Optional;

/**
 * DTO contains title fields.
 * Only filled fields are visible (even if they're filled with nulls)
 */
public class TitlesWithDefaultRuDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Optional<String> title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Optional<String> titleRu;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Optional<String> titleEn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Optional<String> titleEs;

    /**
     * Set titles of dto according to locale.
     * If field isn't localized then use fallback locale (Ru)
     * @param entity entity to get titles from
     * @param locale chosen locale
     */
    public void setTitlesOf(Titles entity, Locale locale) {
        if (locale.equals(Locale.ALL)) {
            titleRu = Optional.ofNullable(entity.getTitleRu());
            titleEn = Optional.ofNullable(entity.getTitleEn());
            titleEs = Optional.ofNullable(entity.getTitleEs());
        } else {
            //If title is not localized use default (Ru)
            //Never contains null. Maybe its not good
            String byLocale = entity.getTitleByLocale(locale);
            title = Optional.of(byLocale == null || byLocale.isBlank()
                    ? Optional.ofNullable(entity.getTitleRu()).orElse("")
                    : byLocale);
        }
    }

    public Optional<String> getTitle() {
        return title;
    }

    public void setTitle(Optional<String> title) {
        this.title = title;
    }

    public Optional<String> getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(Optional<String> titleRu) {
        this.titleRu = titleRu;
    }

    public Optional<String> getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(Optional<String> titleEn) {
        this.titleEn = titleEn;
    }

    public Optional<String> getTitleEs() {
        return titleEs;
    }

    public void setTitleEs(Optional<String> titleEs) {
        this.titleEs = titleEs;
    }
}
