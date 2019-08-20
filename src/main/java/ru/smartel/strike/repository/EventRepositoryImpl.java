package ru.smartel.strike.repository;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.model.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private SessionFactory sessionFactory;

    public EventRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Event find(int id) {
        return sessionFactory.getCurrentSession().find(Event.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Event> get(int count) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        query.select(root);

        return sessionFactory.getCurrentSession()
                .createQuery(query)
                .setMaxResults(count)
                .list();
    }

    @Override
    public void store(Event event) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(event);
        sessionFactory.getCurrentSession().persist(event);
        sessionFactory.getCurrentSession().flush();
        transaction.commit();
    }
}
