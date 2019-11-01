package ru.smartel.strike.service.comment;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.dto.request.comment.CommentRequestDTO;
import ru.smartel.strike.dto.request.comment.CommentListRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.comment.CommentDTO;
import ru.smartel.strike.entity.Comment;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.entity.Photo;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.repository.comment.CommentRepository;
import ru.smartel.strike.repository.etc.PhotoRepository;
import ru.smartel.strike.repository.etc.UserRepository;
import ru.smartel.strike.repository.event.EventRepository;
import ru.smartel.strike.repository.news.NewsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private NewsRepository newsRepository;
    private PhotoRepository photoRepository;
    private CommentDTOValidator commentDTOValidator;

    public CommentServiceImpl(CommentRepository commentRepository,
                              UserRepository userRepository,
                              EventRepository eventRepository,
                              NewsRepository newsRepository,
                              PhotoRepository photoRepository,
                              CommentDTOValidator commentDTOValidator) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.newsRepository = newsRepository;
        this.photoRepository = photoRepository;
        this.commentDTOValidator = commentDTOValidator;
    }

    @Override
    public ListWrapperDTO<CommentDTO> list(CommentListRequestDTO dto) {
        Long commentsCount = commentRepository.getCommentsOfEntityCount(dto);

        ListWrapperDTO.Meta responseMeta = new ListWrapperDTO.Meta(
                commentsCount,
                dto.getPage(),
                dto.getPerPage()
        );

        if (commentsCount.equals(0L)) return new ListWrapperDTO<>(Collections.emptyList(), responseMeta);

        List<Comment> comments = commentRepository.getCommentsOfEntityWithPaginationOrderByCreationDate(dto);

        return new ListWrapperDTO<>(
                comments.stream()
                        .map(CommentDTO::new)
                        .collect(toList()),
                responseMeta
        );
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ListWrapperDTO<CommentDTO> getComplained(BaseListRequestDTO dto) {
        Long commentsCount = commentRepository.getCommentsWithClaimsCount();

        ListWrapperDTO.Meta responseMeta = new ListWrapperDTO.Meta(
                commentsCount,
                dto.getPage(),
                dto.getPerPage()
        );

        if (commentsCount.equals(0L)) return new ListWrapperDTO<>(Collections.emptyList(), responseMeta);

        List<Comment> comments = commentRepository.getCommentsWithClaims(dto);

        return new ListWrapperDTO<>(
                comments.stream()
                        .map(CommentDTO::new)
                        .collect(toList()),
                responseMeta
        );
    }

    @Override
    @PreAuthorize("isFullyAuthenticated()")
    public CommentDTO create(CommentRequestDTO dto, Integer userId) throws DTOValidationException {
        commentDTOValidator.validateDTO(dto);

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setUser(userRepository.getOne(userId));

        dto.getPhotoUrls().forEach(photoUrl -> {
            Photo photo = new Photo();
            photo.setUrl(photoUrl);
            comment.getPhotos().add(photo);
        });

        commentRepository.save(comment);

        //todo generify
        if (dto.getOwner().getClazz().equals(Event.class)) {
            Event event = eventRepository.getOne(dto.getOwner().getId());
            event.getComments().add(comment);
            eventRepository.save(event);
        } else if (dto.getOwner().getClazz().equals(News.class)) {
            News news = newsRepository.getOne(dto.getOwner().getId());
            news.getComments().add(comment);
            newsRepository.save(news);
        } else {
            throw new IllegalStateException("cannot find repository for such comment owner");
        }

        return new CommentDTO(comment);
    }

    @Override
    @PreAuthorize("isFullyAuthenticated()") //todo user can update his own comments only
    public CommentDTO update(Integer commentId, CommentRequestDTO dto, Integer userId) throws DTOValidationException {
        commentDTOValidator.validateDTO(dto);

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Комментарий не найден")
        );

        comment.setContent(dto.getContent());
        photoRepository.deleteAll(comment.getPhotos());
        comment.setPhotos(new LinkedHashSet<>());

        for (final String photoUrl : dto.getPhotoUrls()) {
            Photo photo = new Photo();
            photo.setUrl(photoUrl);
            photoRepository.save(photo);
            comment.getPhotos().add(photo);
        }

        commentRepository.save(comment);

        return new CommentDTO(comment);
    }

    @Override
    @PreAuthorize("isFullyAuthenticated()") //todo user can delete his own comments only
    public void delete(Integer commentId, Integer userId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Комментарий не найден")
        );

        commentRepository.delete(comment);
    }
}
