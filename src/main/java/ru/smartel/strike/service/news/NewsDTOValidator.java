package ru.smartel.strike.service.news;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;
import ru.smartel.strike.service.validation.BasePostDTOValidator;

import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Component
public class NewsDTOValidator extends BasePostDTOValidator {

    public void validateListQueryDTO(NewsListRequestDTO dto) {
        Map<String, List<String>> errors = super.validateListQueryDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    public void validateStoreDTO(NewsCreateRequestDTO dto) {
        Map<String, List<String>> errors = super.validateStoreDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    public void validateUpdateDTO(NewsUpdateRequestDTO dto) {
        Map<String, List<String>> errors = super.validateUpdateDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", notNull(), errors);
        }

        if (null == dto.getNewsId()) {
            addErrorMessage("newsId", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
