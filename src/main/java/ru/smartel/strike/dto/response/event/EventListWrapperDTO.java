package ru.smartel.strike.dto.response.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventListWrapperDTO {
    private List<EventListDTO> data;
    private Map<String, Object> meta = new HashMap<>();

    public EventListWrapperDTO(List<EventListDTO> data, Long count) {
        this.data = data;
        meta.put("total", count);
    }

    public List<EventListDTO> getData() {
        return data;
    }

    public void setData(List<EventListDTO> data) {
        this.data = data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }
}
