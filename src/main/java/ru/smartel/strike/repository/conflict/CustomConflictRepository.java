package ru.smartel.strike.repository.conflict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Conflict;

import java.util.List;

@Repository
public interface CustomConflictRepository {

    List<Long> findAllByIdGetParentEventId(List<Long> ids);

    /**
     * Rebuilds nested set tree.
     * This method is extremely expensive.
     * TODO refactor
     */
    void rebuildTree();

    void insertAsLastChildOf(Conflict conflict, Conflict parent);

    boolean hasChildren(Conflict conflict);

    List<Long> findIds(Specification<Conflict> specification, Integer page, Integer perPage);
}
