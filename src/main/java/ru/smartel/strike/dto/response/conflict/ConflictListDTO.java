package ru.smartel.strike.dto.response.conflict;

import ru.smartel.strike.dto.response.TitlesExtendableDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;

public class ConflictListDTO extends TitlesExtendableDTO {

    public ConflictListDTO(Conflict conflict, Locale locale, boolean brief) {
        super(conflict, locale);
        id = conflict.getId();
        if (!brief) {
            add("latitude", conflict.getLatitude());
            add("longitude", conflict.getLongitude());
            add("companyName", conflict.getCompanyName());
            add("dateFrom", null != conflict.getDateFrom() ? conflict.getDateFrom().toEpochSecond(ZoneOffset.UTC) : null);
            add("dateTo", null != conflict.getDateTo() ? conflict.getDateTo().toEpochSecond(ZoneOffset.UTC) : null);
            add("conflictReasonId", null != conflict.getReason() ? conflict.getReason().getId() : null);
            add("conflictResultId", null != conflict.getResult() ? conflict.getResult().getId() : null);
            add("industryId", null != conflict.getIndustry() ? conflict.getIndustry().getId() : null);
            add("parentEventId", null != conflict.getParentEvent() ? conflict.getParentEvent().getId() : null);
        }
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}