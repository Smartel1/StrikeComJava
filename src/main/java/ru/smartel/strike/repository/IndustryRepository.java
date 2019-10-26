package ru.smartel.strike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.reference.Industry;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Integer> {
}
