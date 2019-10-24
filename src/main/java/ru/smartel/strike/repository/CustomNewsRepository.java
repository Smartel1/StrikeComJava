package ru.smartel.strike.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.News;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public interface CustomNewsRepository {
    News findOrThrow(int id) throws EntityNotFoundException;
    List<Integer> findIdsOrderByDateDesc(Specification<News> specification, Integer page, Integer perPage);
}
