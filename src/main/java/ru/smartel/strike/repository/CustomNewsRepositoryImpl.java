package ru.smartel.strike.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.News;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class CustomNewsRepositoryImpl implements CustomNewsRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public News findOrThrow(int id) throws EntityNotFoundException {
        News entity = entityManager.find(News.class, id);
        if (null == entity) throw new EntityNotFoundException("Новость не найдена");
        return entity;
    }

    @Override
    public List<Integer> findIdsOrderByDateDesc(Specification<News> specification, Integer page, Integer perPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> idQuery = cb.createQuery(Integer.class);
        Root<News> root = idQuery.from(News.class);
        idQuery.select(root.get("id"))
                .orderBy(cb.desc(root.get("post").get("date")));

        idQuery.where(specification.toPredicate(root, idQuery, cb));

        return entityManager
                .createQuery(idQuery)
                .setMaxResults(perPage)
                .setFirstResult((page - 1) * perPage)
                .getResultList();
    }
}
