package ru.smartel.strike.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Event;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public interface CustomEventRepository {
    boolean isNotParentForAnyConflicts(int eventId);
    Event findOrThrow(int id) throws EntityNotFoundException;
    List<Integer> findIdsOrderByDateDesc(Specification<Event> specification, Integer page, Integer perPage);
}
