package ru.smartel.strike.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Repository
public interface CustomConflictRepository {

    Conflict findOrThrow(int id) throws EntityNotFoundException;

    List<Integer> findAllByIdGetParentEventId(List<Integer> ids);

    List<Integer> findIds(Specification<Conflict> specification, Integer page, Integer perPage);
}
