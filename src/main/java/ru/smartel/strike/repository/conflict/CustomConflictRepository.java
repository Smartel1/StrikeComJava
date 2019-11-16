package ru.smartel.strike.repository.conflict;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Conflict;

import java.util.List;

@Repository
public interface CustomConflictRepository {

    List<Long> findAllByIdGetParentEventId(List<Long> ids);

    List<Long> findIds(Specification<Conflict> specification, Integer page, Integer perPage);

    /**
     * Persist conflict as child of another conflict of as root (if parent == null).
     * In case of conflict already exists in tree - replace
     * @param conflict transient or persistent conflict
     * @param parent persistent parent conflict or null
     */
    void insertAsLastChildOf(Conflict conflict, Conflict parent);

    /**
     * Return true if conflict has children
     * @param conflict conflict to check
     * @return true if conflict has child conflicts
     */
    boolean hasChildren(Conflict conflict);

    /**
     * Get root conflict of conflict tree
     * @param conflict
     * @return
     */
    Conflict getRootConflict(Conflict conflict);

    /**
     * Get conflict's descendants (not only children)
     * @param conflict
     * @return
     */
    List<Conflict> getDescendantsAndSelf(Conflict conflict);
}
