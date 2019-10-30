package ru.smartel.strike.repository.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.reference.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>, JpaSpecificationExecutor<Region> {
}
