package ru.smartel.strike.repository.etc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByUuid(String uuid);
}
