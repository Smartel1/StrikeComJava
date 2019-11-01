package ru.smartel.strike.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {
}
