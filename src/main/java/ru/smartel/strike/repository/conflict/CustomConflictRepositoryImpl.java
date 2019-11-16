package ru.smartel.strike.repository.conflict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import pl.exsio.nestedj.NestedNodeRepository;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
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

    @Override
    public void insertAsLastChildOf(Conflict conflict, Conflict parent) {
        if (parent != null) {
            conflictNestedNodeRepository.insertAsLastChildOf(conflict, parent);
        } else {
            //saving as conflict with no parent
            conflict.setTreeLevel(0L);
            conflict.setTreeLeft(getMaxRight() + 1);
            conflict.setTreeRight(getMaxRight() + 2);
            entityManager.persist(conflict);
        }
    }

    @Override
    public boolean hasChildren(Conflict conflict) {
        Long childrenCount = (Long) entityManager.createQuery("select count(c) from Conflict c where c.parentId = :id")
                .setParameter("id", conflict.getId())
                .getSingleResult();
        return !childrenCount.equals(0L);
    }

    /**
     * Get max 'treeRight' value of conflicts tree or 0L if tree is empty
     * @return max rgt
     */
    private Long getMaxRight() {
        try {
            return (Long)entityManager.createQuery("select max(treeRight) from Conflict").getSingleResult();
        } catch (Exception ex) {
            //happens if no rows in the table
            return 0L;
        }
    }
}
