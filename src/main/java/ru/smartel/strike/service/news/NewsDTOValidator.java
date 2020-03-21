package ru.smartel.strike.service.news;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.news.NewsCreateRequestDTO;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsUpdateRequestDTO;
import ru.smartel.strike.service.validation.BasePostDTOValidator;
import ru.smartel.strike.util.ValidationUtil;

import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.NotNull;
import static ru.smartel.strike.util.ValidationUtil.addErrorMessage;
import static ru.smartel.strike.util.ValidationUtil.throwIfErrorsExist;

@Component
public class NewsDTOValidator extends BasePostDTOValidator {

    public void validateListQueryDTO(NewsListRequestDTO dto) {
        Map<String, List<String>> errors = super.validateListQueryDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    public void validateStoreDTO(NewsCreateRequestDTO dto) {
        Map<String, List<String>> errors = super.validateStoreDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    public void validateUpdateDTO(NewsUpdateRequestDTO dto) {
        Map<String, List<String>> errors = super.validateUpdateDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getNewsId()) {
            addErrorMessage("newsId", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
