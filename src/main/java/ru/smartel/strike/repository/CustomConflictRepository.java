package ru.smartel.strike.repository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomConflictRepository {
    List<Integer> findAllByIdGetParentEventId(List<Integer> ids);
}
