package ru.smartel.strike.repository.event;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.Event;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public interface CustomEventRepository {
    boolean isNotParentForAnyConflicts(long eventId);
    List<Long> findIdsOrderByDateDesc(Specification<Event> specification, BaseListRequestDTO dto);
}
