package ru.smartel.strike.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Event;

import java.util.List;

@Repository
public interface CustomEventRepository {
    Event findOrThrow(int id);
    List<Integer> findIdsOrderByDateDesc(Specification<Event> specification, Integer page, Integer perPage);
}
