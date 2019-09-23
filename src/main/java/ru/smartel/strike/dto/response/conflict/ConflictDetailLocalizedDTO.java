package ru.smartel.strike.dto.response.conflict;

import lombok.Data;
import ru.smartel.strike.model.Conflict;
import ru.smartel.strike.service.Locale;

@Data
public class ConflictDetailLocalizedDTO extends ConflictDetailDTO {

    private String title;

    public ConflictDetailLocalizedDTO (Conflict conflict, Locale locale) {
        super(conflict);
        switch (locale) {
            case RU: {
                title = conflict.getTitleRu();
                break;
            }
            case EN: {
                title = conflict.getTitleEn();
                break;
            }
            case ES: {
                title = conflict.getTitleEs();
                break;
            }
        }
    }
}