package ru.smartel.strike.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Comment;


@Repository
public interface CommentRepository extends
        JpaRepository<Comment, Integer>,
        JpaSpecificationExecutor<Comment>,
        CustomCommentRepository {
}
