package ru.smartel.strike.repository.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class CustomConflictRepositoryImpl implements CustomConflictRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> findAllByIdGetParentEventId(List<Long> ids) {
        return entityManager.createQuery("select parentEvent.id from Conflict where id in :ids")
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public List<Long> findIds(Specification<Conflict> specification, Integer page, Integer perPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<Conflict> root = idQuery.from(Conflict.class);
        idQuery.select(root.get("id"));

        idQuery.where(specification.toPredicate(root, idQuery, cb));

        return entityManager.createQuery(idQuery)
                .setMaxResults(perPage)
                .setFirstResult((page - 1) * perPage)
                .getResultList();
    }
}
