package ru.smartel.strike.repository.conflict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import pl.exsio.nestedj.NestedNodeRepository;
import ru.smartel.strike.dto.service.sort.ConflictSortDTO;
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
    public List<Long> findIds(Specification<Conflict> specification, ConflictSortDTO sortDTO, Integer page, Integer perPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<Conflict> root = idQuery.from(Conflict.class);
        idQuery.select(root.get("id"));
        idQuery.orderBy(sortDTO.toOrder(cb, root));
        idQuery.where(specification.toPredicate(root, idQuery, cb));

        return entityManager.createQuery(idQuery)
                .setMaxResults(perPage)
                .setFirstResult((page - 1) * perPage)
                .getResultList();
    }

    @Override
    public void insertAsLastChildOf(Conflict conflict, Conflict parent) {
        if (parent == null) {
            if (conflict.getParentId() != null || conflict.getTreeRight() == null) {
                // set service fields if not set or if set incorrect
                conflictNestedNodeRepository.insertAsLastRoot(conflict);
            }
        } else {
            if (!parent.getId().equals(conflict.getParentId()) || conflict.getTreeRight() == null) {
                // set service fields if not set or if set incorrect
                conflictNestedNodeRepository.insertAsLastChildOf(conflict, parent);
            }
        }
        entityManager.persist(conflict);
    }

    @Override
    public boolean hasChildren(Conflict conflict) {
        Long childrenCount = (Long) entityManager.createQuery("select count(c) from Conflict c where c.parentId = :id")
                .setParameter("id", conflict.getId())
                .getSingleResult();
        return !childrenCount.equals(0L);
    }

    @Override
    public void deleteFromTree(Conflict conflict) {
        conflictNestedNodeRepository.removeSingle(conflict);
    }

    @Override
    public Conflict getRootConflict(Conflict conflict) {
        return (Conflict) entityManager.createQuery(
                "select c from Conflict c where treeLevel = :rootLevel and treeLeft <= :lft and treeRight >= :rgt")
                .setParameter("rootLevel", 0L)
                .setParameter("rgt", conflict.getTreeRight())
                .setParameter("lft", conflict.getTreeLeft())
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Conflict> getDescendantsAndSelf(Conflict conflict) {
        return entityManager.createQuery(
                "select c from Conflict c where treeLeft >= :lft and treeRight <= :rgt")
                .setParameter("rgt", conflict.getTreeRight())
                .setParameter("lft", conflict.getTreeLeft())
                .getResultList();
    }
}
