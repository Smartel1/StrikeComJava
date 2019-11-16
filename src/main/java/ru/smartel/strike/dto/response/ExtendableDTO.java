package ru.smartel.strike.dto.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс-заготовка для DTO, которые содержат необязательные поля.
 * Такие поля сериализуются в json как обычные поля класса
 */
public class ExtendableDTO {

    private Map<String, Object> optionalFields = new HashMap<>();

    public void add(String key, Object value) {
        optionalFields.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getOptionalFields() {
        return optionalFields;
    }

    @JsonAnySetter
    public void setOptionalFields(Map<String, Object> optionalFields) {
        this.optionalFields = optionalFields;
    }
}
