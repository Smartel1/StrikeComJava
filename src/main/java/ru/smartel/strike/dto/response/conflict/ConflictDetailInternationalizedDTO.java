package ru.smartel.strike.dto.response.conflict;

import lombok.Data;
import ru.smartel.strike.model.Conflict;

@Data
public class ConflictDetailInternationalizedDTO extends ConflictDetailDTO {

    private String titleRu;
    private String titleEn;
    private String titleEs;

    public ConflictDetailInternationalizedDTO(Conflict conflict) {
        super(conflict);
        titleRu = conflict.getTitleRu();
        titleEn = conflict.getTitleEn();
        titleEs = conflict.getTitleEs();
    }
}