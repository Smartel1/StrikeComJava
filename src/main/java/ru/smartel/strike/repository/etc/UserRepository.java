package ru.smartel.strike.repository.etc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findFirstByUuid(String uuid);
}
