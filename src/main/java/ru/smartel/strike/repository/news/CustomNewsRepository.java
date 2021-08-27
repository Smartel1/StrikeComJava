package ru.smartel.strike.repository.news;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.dto.service.sort.NewsSortDTO;
import ru.smartel.strike.entity.News;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

@Repository
public interface CustomNewsRepository {
    List<Long> findIds(Specification<News> specification, NewsSortDTO sortDTO, Integer page, Integer perPage);

    void incrementViews(Collection<Long> ids);
}
