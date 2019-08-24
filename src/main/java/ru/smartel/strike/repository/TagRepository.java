package ru.smartel.strike.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
    Tag findFirstByName(String name);
}
