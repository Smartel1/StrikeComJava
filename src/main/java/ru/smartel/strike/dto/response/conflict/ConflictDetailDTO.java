package ru.smartel.strike.dto.response.conflict;

import lombok.Data;
import ru.smartel.strike.dto.response.TitlesExtendableDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;

@Data
public class ConflictDetailDTO extends TitlesExtendableDTO {

    public ConflictDetailDTO (Conflict conflict, Locale locale) {
        super(conflict, locale);
        id = conflict.getId();
        latitude = conflict.getLatitude();
        longitude = conflict.getLongitude();
        companyName = conflict.getCompanyName();
        dateFrom = null != conflict.getDateFrom() ? conflict.getDateFrom().toEpochSecond(ZoneOffset.UTC) : null;
        dateTo = null != conflict.getDateTo() ? conflict.getDateTo().toEpochSecond(ZoneOffset.UTC) : null;
        conflictReasonId = null != conflict.getConflictReason() ? conflict.getConflictReason().getId() : null;
        conflictResultId = null != conflict.getConflictResult() ? conflict.getConflictResult().getId() : null;
        industryId = null != conflict.getIndustry() ? conflict.getIndustry().getId() : null;
        parentEventId = null != conflict.getParentEvent() ? conflict.getParentEvent().getId() : null;
    }

    private int id;
    private double latitude;
    private double longitude;
    private String companyName;
    private Long dateFrom;
    private Long dateTo;
    private Integer conflictReasonId;
    private Integer conflictResultId;
    private Integer industryId;
    private Integer parentEventId;
}