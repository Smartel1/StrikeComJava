package ru.smartel.strike.repository.news;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.News;

import java.util.List;


@Repository
public interface NewsRepository extends CustomNewsRepository, JpaSpecificationExecutor<News>, JpaRepository<News, Long> {
    @EntityGraph(attributePaths = {"videos", "photos", "tags"})
    @Override
    List<News> findAllById(Iterable<Long> ids);
}
