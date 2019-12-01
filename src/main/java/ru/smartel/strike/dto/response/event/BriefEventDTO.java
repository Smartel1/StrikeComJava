package ru.smartel.strike.dto.response.event;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import ru.smartel.strike.dto.response.TitlesWithDefaultRuDTO;
import ru.smartel.strike.entity.interfaces.HavingTitles;
import ru.smartel.strike.service.Locale;

public class BriefEventDTO {
    private long id;
    private long date;
    @JsonUnwrapped
    private TitlesWithDefaultRuDTO titles = new TitlesWithDefaultRuDTO();

    public void setTitlesOf(HavingTitles entity, Locale locale) {
        titles.setTitlesOf(entity, locale);
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
