package ru.smartel.strike.dto.response.event;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class WithRelativesEventDetailDTO extends EventDTODecorator {

    private List<Object> relatives;

    public WithRelativesEventDetailDTO(EventDetailDTO wrappee) {
        super(wrappee);
        this.relatives = Collections.emptyList();
    }
}
