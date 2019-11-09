package ru.smartel.strike.repository.conflict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Repository
public interface CustomConflictRepository {

    List<Long> findAllByIdGetParentEventId(List<Long> ids);

    List<Long> findIds(Specification<Conflict> specification, Integer page, Integer perPage);
}
