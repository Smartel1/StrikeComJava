package ru.smartel.strike.service.impl;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.dto.request.video.VideoDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.reference.EventStatus;
import ru.smartel.strike.entity.reference.EventType;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.BaseDTOValidator;
import ru.smartel.strike.service.EventDTOValidator;
import ru.smartel.strike.service.VideoDTOValidator;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Service
public class EventDTOValidatorImpl extends BaseDTOValidator implements EventDTOValidator {

    private VideoDTOValidator videoDTOValidator;

    public EventDTOValidatorImpl(EntityManager entityManager, VideoDTOValidator videoDTOValidator) {
        super(entityManager);
        this.videoDTOValidator = videoDTOValidator;
    }

    @Override
    public void validateListQueryDTO(EventListRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getPage(), "page", errors).min(1);
        check(dto.getPerPage(), "per_page", errors).min(1);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    @Override
    public void validateStoreDTO(EventRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getConflictId(), "conflict_id", errors).requiredOptional().notNull(true).existsAsId(Conflict.class);
        check(dto.getDate(), "date", errors).requiredOptional().notNull();
        check(dto.getLatitude(), "latitude", errors).requiredOptional().notNull();
        check(dto.getLongitude(), "longitude", errors).requiredOptional().notNull();
        validateCommon(dto, errors);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    @Override
    public void validateUpdateDTO(EventRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getConflictId(), "conflict_id", errors).notNull(true).existsAsId(Conflict.class);
        check(dto.getDate(), "date", errors).notNull();
        check(dto.getLatitude(), "latitude", errors).notNull();
        check(dto.getLongitude(), "longitude", errors).notNull();
        validateCommon(dto, errors);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    /**
     * Validate fields whose checks are similar on update and create actions
     */
    private void validateCommon(EventRequestDTO dto, Map<String, String> errors) {
        check(dto.getPublished(), "published", errors).notNull();
        check(dto.getEventStatusId(), "event_status_id", errors).existsAsId(EventStatus.class);
        check(dto.getEventTypeId(), "event_type_id", errors).existsAsId(EventType.class);
        check(dto.getLocalityId(), "locality_id", errors).existsAsId(Locality.class);
        check(dto.getTitle(), "title", errors).maxLength(255);
        check(dto.getTitleRu(), "title_ru", errors).maxLength(255);
        check(dto.getTitleEn(), "title_en", errors).maxLength(255);
        check(dto.getTitleEs(), "title_es", errors).maxLength(255);
        check(dto.getSourceLink(), "source_link", errors).maxLength(255);
        check(dto.getContent(), "content", errors).minLength(3);
        check(dto.getContentRu(), "content_ru", errors).minLength(3);
        check(dto.getContentEn(), "content_en", errors).minLength(3);
        check(dto.getContentEs(), "content_es", errors).minLength(3);

        int i = 0;
        for (String photoUrl : dto.getPhotoUrls()) {
            check(photoUrl, "photo_urls[" + i + "]", errors).maxLength(500);
            i++;
        }

        int k = 0;
        for (String tag : dto.getTags()) {
            check(tag, "tags[" + k + "]", errors).minLength(2).maxLength(20);
            k++;
        }

        for (VideoDTO videoDTO : dto.getVideos()) {
            errors.putAll(videoDTOValidator.getErrors(videoDTO));
        }
    }
}
