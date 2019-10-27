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
    public Conflict findOrThrow(int id) throws EntityNotFoundException {
        Conflict conflict = entityManager.find(Conflict.class, id);
        if (null == conflict) throw new EntityNotFoundException("Конфликт не найден");
        return conflict;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> findAllByIdGetParentEventId(List<Integer> ids) {
        return entityManager.createQuery("select parentEvent.id from Conflict where id in :ids")
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public List<Integer> findIds(Specification<Conflict> specification, Integer page, Integer perPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> idQuery = cb.createQuery(Integer.class);
        Root<Conflict> root = idQuery.from(Conflict.class);
        idQuery.select(root.get("id"));

        idQuery.where(specification.toPredicate(root, idQuery, cb));

        return entityManager.createQuery(idQuery)
                .setMaxResults(perPage)
                .setFirstResult((page - 1) * perPage)
                .getResultList();
    }
}
