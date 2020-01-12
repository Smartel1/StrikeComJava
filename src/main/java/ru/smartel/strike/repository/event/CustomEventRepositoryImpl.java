package ru.smartel.strike.repository.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.dto.service.sort.EventSortDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean isNotParentForAnyConflicts(long eventId) {
        Long count = (Long) entityManager.createQuery(
                "select count(c.id) " +
                        "from " + Conflict.class.getName() + " c " +
                        "where c.parentEvent = " + eventId)
                .setMaxResults(1)
                .getSingleResult();
        return count.equals(0L);
    }

    @Override
    public List<Long> findIds(Specification<Event> specification, EventSortDTO sortDTO, Integer page, Integer perPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<Event> root = idQuery.from(Event.class);
        idQuery.select(root.get("id"))
                .where(specification.toPredicate(root, idQuery, cb))
                .orderBy(sortDTO.toOrder(cb, root));

        return entityManager.createQuery(idQuery)
                .setMaxResults(perPage)
                .setFirstResult((page - 1) * perPage)
                .getResultList();
    }
}
