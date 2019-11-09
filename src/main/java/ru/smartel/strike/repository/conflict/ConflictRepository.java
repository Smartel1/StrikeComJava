package ru.smartel.strike.repository.conflict;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Conflict;

@Repository
public interface ConflictRepository extends JpaRepository<Conflict, Long>, CustomConflictRepository,
        JpaSpecificationExecutor<Conflict> {
}
