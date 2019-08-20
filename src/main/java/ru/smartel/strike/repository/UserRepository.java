package ru.smartel.strike.repository;

import ru.smartel.strike.model.User;

import javax.persistence.PersistenceException;

public interface UserRepository {

    public User findOneByUuid(String uuid) throws PersistenceException;

    public void save(User user);
}
