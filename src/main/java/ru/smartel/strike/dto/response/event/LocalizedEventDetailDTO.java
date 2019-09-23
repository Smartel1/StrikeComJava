package ru.smartel.strike.dto.response.event;

import lombok.Data;
import ru.smartel.strike.model.Event;
import ru.smartel.strike.service.Locale;

@Data
public class LocalizedEventDetailDTO extends EventDTODecorator {

    private String title;
    private String content;

    public LocalizedEventDetailDTO(Event event, Locale locale, EventDetailDTO wrappee) {
        super(wrappee);
        switch (locale) {
            case RU: {
                title = event.getTitleRu();
                content = event.getContentRu();
                break;
            }
            case EN: {
                title = event.getTitleEn();
                content = event.getContentEn();
                break;
            }
            case ES: {
                title = event.getTitleEs();
                content = event.getContentEs();
                break;
            }
        }
    }
}
