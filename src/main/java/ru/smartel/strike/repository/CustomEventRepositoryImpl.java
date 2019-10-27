package ru.smartel.strike.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


public class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean isNotParentForAnyConflicts(int eventId) {
        Long count = (Long) entityManager.createQuery(
                "select count(c.id) " +
                        "from " + Conflict.class.getName() + " c " +
                        "where c.parentEvent = " + eventId)
                .setMaxResults(1)
                .getSingleResult();
        return count.equals(0L);
    }


    @Override
    public Event findOrThrow(int id) throws EntityNotFoundException {
        return Optional.ofNullable(entityManager.find(Event.class, id)).orElseThrow(
                () -> new EntityNotFoundException("Событие не найдено")
        );
    }

    @Override
    public List<Integer> findIdsOrderByDateDesc(Specification<Event> specification, BaseListRequestDTO dto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> idQuery = cb.createQuery(Integer.class);
        Root<Event> root = idQuery.from(Event.class);
        idQuery.select(root.get("id"))
                .orderBy(cb.desc(root.get("post").get("date")));

        idQuery.where(specification.toPredicate(root, idQuery, cb));

        return entityManager.createQuery(idQuery)
                .setMaxResults(dto.getPerPage())
                .setFirstResult((dto.getPage() - 1) * dto.getPerPage())
                .getResultList();
    }
}
