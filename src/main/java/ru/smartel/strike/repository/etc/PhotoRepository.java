package ru.smartel.strike.repository.etc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
