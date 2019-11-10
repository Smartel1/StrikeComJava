package ru.smartel.strike.repository.conflict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import pl.exsio.nestedj.NestedNodeRepository;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class CustomConflictRepositoryImpl implements CustomConflictRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private NestedNodeRepository<Long, Conflict> conflictNestedNodeRepository;

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> findAllByIdGetParentEventId(List<Long> ids) {
        return entityManager.createQuery("select parentEvent.id from Conflict where id in :ids")
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public void saveManagingNestedTree(Conflict conflict) {
        if (null == conflict.getParentEvent()) {
            //If this conflict has no parent, then set lvl=0; lft and rgt according to nested set spec
            Long maxRight = getMaxRight();
            conflict.setTreeLeft(maxRight + 1);
            conflict.setTreeRight(maxRight + 2);
            conflict.setTreeLevel(0L);
            entityManager.persist(conflict);
        } else {
            //If conflict has parent conflict (through parentEvent) - let NestedNodeRepository rule it
            Conflict parentConflict = conflict.getParentEvent().getConflict();
            conflictNestedNodeRepository.insertAsLastChildOf(conflict, parentConflict);
        }
    }

    private Long getMaxRight() {
        try {
            return (Long)entityManager.createQuery("select max(treeRight) from Conflict").getSingleResult();
        } catch (Exception ex) {
            //happens if no rows in the table
            return 0L;
        }
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
