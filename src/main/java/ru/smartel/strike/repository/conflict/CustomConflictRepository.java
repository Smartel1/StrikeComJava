package ru.smartel.strike.repository.conflict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Conflict;

import java.util.List;

@Repository
public interface CustomConflictRepository {

    List<Long> findAllByIdGetParentEventId(List<Long> ids);

    void saveManagingNestedTree(Conflict conflict);

    List<Long> findIds(Specification<Conflict> specification, Integer page, Integer perPage);
}
