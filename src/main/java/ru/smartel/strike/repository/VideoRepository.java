package ru.smartel.strike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
}
