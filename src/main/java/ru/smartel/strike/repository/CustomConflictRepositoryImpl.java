package ru.smartel.strike.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class CustomConflictRepositoryImpl implements CustomConflictRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> findAllByIdGetParentEventId(List<Integer> ids) {
        return entityManager
                .createQuery("select parentEvent.id from Conflict where id in :ids")
                .setParameter("ids", ids)
                .getResultList();
    }
}
