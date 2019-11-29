package ru.smartel.strike.dto.response.conflict;

import ru.smartel.strike.dto.response.TitlesExtendableDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.Optional;

public class ConflictListDTO extends TitlesExtendableDTO {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
            instance.add("createdAt", Optional.ofNullable(conflict.getCreatedAt())
                    .map(d -> d.toEpochSecond(ZoneOffset.UTC))
                    .orElse(null));
        }
        return instance;
    }
}