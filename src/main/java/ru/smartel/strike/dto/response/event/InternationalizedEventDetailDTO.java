package ru.smartel.strike.dto.response.event;

import lombok.Data;
import ru.smartel.strike.model.Event;

@Data
public class InternationalizedEventDetailDTO extends EventDTODecorator {

    private String titleRu;
    private String titleEn;
    private String titleEs;
    private String contentRu;
    private String contentEn;
    private String contentEs;

    public InternationalizedEventDetailDTO(Event event, EventDetailDTO wrappee) {
        super(wrappee);
        titleRu = event.getTitleRu();
        titleEn = event.getTitleEn();
        titleEs = event.getTitleEs();
        contentRu = event.getContentRu();
        contentEn = event.getContentEn();
        contentEs = event.getContentEs();
    }
}
