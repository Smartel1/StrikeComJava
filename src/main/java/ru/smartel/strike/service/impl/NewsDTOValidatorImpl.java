package ru.smartel.strike.service.impl;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.NewsDTOValidator;

@Service
public class NewsDTOValidatorImpl extends BasePostDTOValidator implements NewsDTOValidator {

    @Override
    public void validateListQueryDTO(NewsListRequestDTO dto) throws DTOValidationException {
        super.validateListQueryDTO(dto);
    }

    @Override
    public void validateStoreDTO(NewsRequestDTO dto) throws DTOValidationException {
        super.validateStoreDTO(dto);
    }

    @Override
    public void validateUpdateDTO(NewsRequestDTO dto) throws DTOValidationException {
        super.validateUpdateDTO(dto);
    }
}
