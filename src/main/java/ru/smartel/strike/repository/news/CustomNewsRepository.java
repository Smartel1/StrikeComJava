package ru.smartel.strike.repository.news;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.News;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public interface CustomNewsRepository {
    News findOrThrow(int id) throws EntityNotFoundException;
    List<Integer> findIdsOrderByDateDesc(Specification<News> specification, BaseListRequestDTO dto);
}
