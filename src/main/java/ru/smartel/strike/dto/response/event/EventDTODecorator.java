package ru.smartel.strike.dto.response.event;

import ru.smartel.strike.dto.response.DTODecorator;

/**
 * Для построения овтетов используется паттерн Декоратор.
 * Наследование не годится, ведь ответ формируется как результат комбинации разных классов.
 * В зависимости от нужд может появиться большое количество таких комбинаций.
 */
public class EventDTODecorator extends DTODecorator implements EventDetailDTO {
    public EventDTODecorator(EventDetailDTO wrappee) {
        super(wrappee);
    }
}
