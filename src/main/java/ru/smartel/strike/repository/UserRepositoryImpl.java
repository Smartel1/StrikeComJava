package ru.smartel.strike.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.model.User;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findOneByUuid(String uuid) throws PersistenceException {
        return sessionFactory.getCurrentSession()
                .createQuery("select u from User u where uuid = :uuid", User.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().persist(user);
        sessionFactory.getCurrentSession().flush();
    }
}
