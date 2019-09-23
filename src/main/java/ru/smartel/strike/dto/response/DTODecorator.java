package ru.smartel.strike.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

/**
 * При сериализации DTO в json необходимо комбинировать различные поля.
 * Поэтому возникает необходимость использовать паттерн Декоратор.
 * Jackson2 делает flatten полей, помеченных аннотацией @JsonUnwrapped (отображает поля объектов прямо в корне)
 */
@Data
public abstract class DTODecorator implements ResponseDTO {

    public DTODecorator(ResponseDTO wrappee) {
        this.wrappee = wrappee;
    }

    @JsonUnwrapped
    private ResponseDTO wrappee;
}
