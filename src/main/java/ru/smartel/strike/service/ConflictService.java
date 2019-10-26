package ru.smartel.strike.service;

import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.conflict.ConflictListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;

public interface ConflictService {

    ListWrapperDTO<ConflictListDTO> list(ConflictListRequestDTO dto, int perPage, int page, Locale locale, User user, boolean brief)
            throws DTOValidationException;

    ConflictDetailDTO get(Integer conflictId, Locale locale);

    ConflictDetailDTO create(ConflictRequestDTO dto, Integer userId, Locale locale)
            throws BusinessRuleValidationException, DTOValidationException;

    ConflictDetailDTO update(Integer conflictId, ConflictRequestDTO dto, Integer userId, Locale locale)
            throws BusinessRuleValidationException, DTOValidationException;

    void delete(Integer conflictId) throws BusinessRuleValidationException;
}
