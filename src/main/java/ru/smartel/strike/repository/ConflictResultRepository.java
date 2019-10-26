package ru.smartel.strike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.reference.ConflictResult;

@Repository
public interface ConflictResultRepository extends JpaRepository<ConflictResult, Integer> {
}
