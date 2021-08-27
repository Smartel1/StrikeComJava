package ru.smartel.strike.repository.news;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.dto.service.sort.NewsSortDTO;
import ru.smartel.strike.entity.News;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;


public class CustomNewsRepositoryImpl implements CustomNewsRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Long> findIds(Specification<News> specification, NewsSortDTO sortDTO, Integer page, Integer perPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<News> root = idQuery.from(News.class);
        idQuery.select(root.get("id"))
                .where(specification.toPredicate(root, idQuery, cb))
                .orderBy(sortDTO.toOrder(cb, root));

        return entityManager.createQuery(idQuery)
                .setMaxResults(perPage)
                .setFirstResult((page - 1) * perPage)
                .getResultList();
    }

    @Override
    public void incrementViews(Collection<Long> ids) {
        entityManager.createNativeQuery("update news set views = views + 1 where id in :ids")
                .setParameter("ids", ids)
                .executeUpdate();
    }
}
