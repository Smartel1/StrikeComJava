package ru.smartel.strike.dto.response.event;

import ru.smartel.strike.model.Event;
import ru.smartel.strike.service.Locale;

/**
 * Класс является фасадом для функционала построения объекта, отражающего структуру ответа.
 * Он скрывает сложности формирования EventDetailDTO
 */
public class EventDetailDTOCreator {
    /**
     * Хэлпер, создающий экземпляр EventDetailDTO.
     * Если передано withRelatives=true, то в ответе появится поле relatives.
     * В зависимости от локали будут выведены соответствующие поля title или title_ru,es,en и т.д.
     */
    public static EventDetailDTO create(Event event, Locale locale, boolean withRelatives) {

        EventDetailDTO result = new BaseEventDetailDTO(event, locale);

        if (locale == Locale.ALL) {
            result = new InternationalizedEventDetailDTO(event, result);
        } else {
            result = new LocalizedEventDetailDTO(event, locale, result);
        }

        if (withRelatives) {
            result = new WithRelativesEventDetailDTO(result);
        }

        return result;
    }

}
