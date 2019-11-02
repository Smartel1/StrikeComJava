package ru.smartel.strike.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.reference.ClaimType;

@Repository
public interface ClaimTypeRepository extends JpaRepository<ClaimType, Integer> {
}
