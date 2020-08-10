package ru.smartel.strike.repository.event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.dto.service.event.type.EventTypeCountDTO;
import ru.smartel.strike.dto.service.sort.EventSortDTO;
import ru.smartel.strike.entity.Event;

import java.util.List;

@Repository
public interface CustomEventRepository {
    boolean isNotParentForAnyConflicts(long eventId);
    List<Long> findIds(Specification<Event> specification, EventSortDTO sortDTO, Integer page, Integer perPage);

    /**
     * Count of each event types for specified conflict. Null-types ignored
     *
     * @param conflictId conlict id
     * @return typeId to count
     */
    List<EventTypeCountDTO> getEventTypesCountByConflictId(long conflictId);
}
