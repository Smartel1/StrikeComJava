package ru.smartel.strike.repository.comment;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.dto.request.comment.CommentListRequestDTO;
import ru.smartel.strike.entity.Comment;
import ru.smartel.strike.entity.interfaces.HasComments;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    //todo eagerly load comments' claims
    public List<Comment> getCommentsOfEntityWithPaginationOrderByCreationDate(CommentListRequestDTO dto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> idQuery = cb.createQuery(Comment.class);
        Root<? extends HasComments> root = idQuery.from(dto.getOwner().getClazz());
        Path<Comment> commentsPath = root.join("comments");

        idQuery.select(commentsPath)
                .where(cb.equal(root.get("id"), dto.getOwner().getId()))
                .orderBy(cb.desc(commentsPath.get("createdAt")));

        return entityManager.createQuery(idQuery)
                .setMaxResults(dto.getPerPage())
                .setFirstResult((dto.getPage() - 1) * dto.getPerPage())
                .getResultList();
    }

    @Override
    public Long getCommentsOfEntityCount(CommentListRequestDTO dto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<? extends HasComments> root = idQuery.from(dto.getOwner().getClazz());

        idQuery.select(cb.count(root.join("comments")))
                .where(cb.equal(root.get("id"), dto.getOwner().getId()));

        return entityManager.createQuery(idQuery)
                .getSingleResult();
    }

    @Override
    public List<Comment> getCommentsWithClaims(BaseListRequestDTO dto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> idQuery = cb.createQuery(Comment.class);
        Root<Comment> root = idQuery.from(Comment.class);

        idQuery.select(root)
                .where(cb.isNotEmpty(root.get("claims")))
                .orderBy(cb.desc(root.get("createdAt")));

        return entityManager.createQuery(idQuery)
                .setMaxResults(dto.getPerPage())
                .setFirstResult((dto.getPage() - 1) * dto.getPerPage())
                .getResultList();
    }

    @Override
    public Long getCommentsWithClaimsCount() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<Comment> root = idQuery.from(Comment.class);

        idQuery.select(cb.count(root))
                .where(cb.isNotEmpty(root.get("claims")));

        return entityManager.createQuery(idQuery)
                .getSingleResult();
    }
}
