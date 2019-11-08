package ru.smartel.strike.dto.response.conflict;

import ru.smartel.strike.dto.response.TitlesExtendableDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;

public class ConflictListDTO extends TitlesExtendableDTO {

    public static ConflictListDTO of(Conflict conflict, Locale locale, boolean brief) {
        ConflictListDTO instance = new ConflictListDTO();
        instance.setTitlesOf(conflict, locale);
        instance.setId(conflict.getId());
        if (!brief) {
            instance.add("latitude", conflict.getLatitude());
            instance.add("longitude", conflict.getLongitude());
            instance.add("companyName", conflict.getCompanyName());
            instance.add("dateFrom", null != conflict.getDateFrom() ? conflict.getDateFrom().toEpochSecond(ZoneOffset.UTC) : null);
            instance.add("dateTo", null != conflict.getDateTo() ? conflict.getDateTo().toEpochSecond(ZoneOffset.UTC) : null);
            instance.add("conflictReasonId", null != conflict.getReason() ? conflict.getReason().getId() : null);
            instance.add("conflictResultId", null != conflict.getResult() ? conflict.getResult().getId() : null);
            instance.add("industryId", null != conflict.getIndustry() ? conflict.getIndustry().getId() : null);
            instance.add("parentEventId", null != conflict.getParentEvent() ? conflict.getParentEvent().getId() : null);
        }
        return instance;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}