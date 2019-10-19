package ru.smartel.strike.service.impl;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.video.VideoDTO;
import ru.smartel.strike.entity.reference.VideoType;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.AbstractDTOValidator;
import ru.smartel.strike.service.VideoDTOValidator;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Service
public class VideoDTOValidatorImpl extends AbstractDTOValidator implements VideoDTOValidator {

    public VideoDTOValidatorImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void validate(VideoDTO dto) throws DTOValidationException {
        Map<String, String> errors = getErrors(dto);
        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    @Override
    public Map<String, String> getErrors(VideoDTO dto) {
        Map<String, String> errors = new HashMap<>();

        check(dto.getUrl(), "url", errors).notNull().maxLength(500);
        check(dto.getPreviewUrl(), "preview_url", errors).maxLength(500);
        check(dto.getVideoTypeId(), "video_type_id", errors).existsAsId(VideoType.class);

        return errors;
    }
}
