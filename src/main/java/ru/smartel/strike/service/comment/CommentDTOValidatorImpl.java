package ru.smartel.strike.service.comment;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.comment.CommentCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.validation.BaseDTOValidator;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentDTOValidatorImpl extends BaseDTOValidator implements CommentDTOValidator {

    @Override
    public void validateDTO(CommentCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getContent(), "content", errors).notNull().minLength(1);

        if (null != dto.getPhotoUrls()) {
            int i = 0;
            for (String photoUrl : dto.getPhotoUrls()) {
                check(photoUrl, "photo_urls[" + i + "]", errors).maxLength(500);
                i++;
            }
        }

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }
}
