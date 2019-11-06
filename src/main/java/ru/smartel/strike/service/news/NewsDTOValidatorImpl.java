package ru.smartel.strike.service.news;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.validation.BasePostDTOValidator;
import ru.smartel.strike.util.ValidationUtil;

import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class NewsDTOValidatorImpl extends BasePostDTOValidator implements NewsDTOValidator {

    @Override
    public void validateListQueryDTO(NewsListRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = super.validateListQueryDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateStoreDTO(NewsCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = super.validateStoreDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateUpdateDTO(NewsUpdateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = super.validateUpdateDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getNewsId()) {
            addErrorMessage("news_id", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
