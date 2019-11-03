package ru.smartel.strike.service.comment;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.comment.CommentCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

import java.util.HashMap;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class CommentDTOValidatorImpl implements CommentDTOValidator {

    @Override
    public void validateDTO(CommentCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getUser()) {
            addErrorMessage("user", new NotNull(), errors);
        }

        if (null == dto.getContent()) {
            addErrorMessage("content", new NotNull(), errors);
        } else if (dto.getContent().length() < 1) {
            addErrorMessage("content", new Min(1), errors);
        }

        if (null != dto.getPhotoUrls()) {
            int i = 0;
            for (String photoUrl : dto.getPhotoUrls()) {
                if (photoUrl.length() > 500) {
                    addErrorMessage("photo_urls[" + i + "]", new Max(500), errors);
                }
                i++;
            }
        }

        throwIfErrorsExist(errors);
    }
}
