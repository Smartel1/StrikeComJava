package ru.smartel.strike.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import ru.smartel.strike.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public  class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Event findOrThrow(int id) {
        Event event = entityManager.find(Event.class, id);
        if (null == event) throw new EntityNotFoundException("Событие не найдено");
        return event;
    }

    @Override
    public List<Integer> findIdsOrderByDateDesc(Specification<Event> specification, Integer page, Integer perPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> idQuery = cb.createQuery(Integer.class);
        Root<Event> root = idQuery.from(Event.class);
        idQuery.select(root.get("id"))
                .orderBy(cb.desc(root.get("date")));

        idQuery.where(specification.toPredicate(root, idQuery, cb));

        return entityManager
                .createQuery(idQuery)
                .setMaxResults(perPage)
                .setFirstResult((page - 1) * perPage)
                .getResultList();
    }
}
