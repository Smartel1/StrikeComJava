package ru.smartel.strike.repository.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.dto.service.event.type.EventTypeCountDTO;
import ru.smartel.strike.dto.service.sort.EventSortDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings("unchecked")
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

    @Override
    public List<EventTypeCountDTO> getEventTypesCountByConflictId(long conflictId) {
        List<Object[]> countByTypeId = (List<Object[]>) entityManager.createNativeQuery(
                "select event_type_id, count, priority" +
                        " from (select e.event_type_id, count(e.id) count" +
                        "      from events e" +
                        "      where e.conflict_id = :conflictId" +
                        "        and e.event_type_id is not null" +
                        "      group by e.event_type_id) sub" +
                        "         left join event_types et on et.id = event_type_id")
                .setParameter("conflictId", conflictId)
                .getResultList();
        return countByTypeId.stream()
                .map(raw -> new EventTypeCountDTO((long) (int) raw[0], ((BigInteger) raw[1]).longValue(), (int) raw[2]))
                .collect(Collectors.toList());
    }

    @Override
    public void incrementViews(Collection<Long> ids) {
        entityManager.createNativeQuery("update events set views = views + 1 where id in :ids")
                .setParameter("ids", ids)
                .executeUpdate();
    }
}
