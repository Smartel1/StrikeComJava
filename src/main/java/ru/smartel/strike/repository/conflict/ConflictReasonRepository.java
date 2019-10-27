package ru.smartel.strike.repository.conflict;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.reference.ConflictReason;

@Repository
public interface ConflictReasonRepository extends JpaRepository<ConflictReason, Integer> {
}
